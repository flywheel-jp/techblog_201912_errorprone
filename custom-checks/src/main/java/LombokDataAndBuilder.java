import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.fixes.SuggestedFix;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.Matcher;
import com.google.errorprone.matchers.Matchers;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import java.util.List;

@AutoService(BugChecker.class)
@BugPattern(
    name = "LombokDataAndBuilder",
    linkType = BugPattern.LinkType.CUSTOM,
    link = "https://projectlombok.org/features/Value",
    tags = BugPattern.StandardTags.FRAGILE_CODE,
    summary = "@lombok.Builder and @lombok.Data rarely need to be attached to a class at once.",
    explanation =
        "@lombok.Builder may be useless for fully mutable @lombok.Data class. "
            + "Consider to make the class immutable, or omit @lombok.Builder for fully mutable @lombok.Data class.",
    severity = BugPattern.SeverityLevel.WARNING)
@SuppressWarnings("serial")
public class LombokDataAndBuilder extends BugChecker implements BugChecker.ClassTreeMatcher {
  private static final Matcher<AnnotationTree> IS_DATA = Matchers.isType("lombok.Data");
  private static final Matcher<AnnotationTree> IS_BUILDER = Matchers.isType("lombok.Builder");

  @Override
  public Description matchClass(ClassTree tree, VisitorState state) {
    List<? extends AnnotationTree> annotations = tree.getModifiers().getAnnotations();
    return annotations.stream()
        .filter(ann -> IS_BUILDER.matches(ann, state))
        .findAny()
        .flatMap(
            builderAnn ->
                annotations.stream()
                    .filter(ann -> IS_DATA.matches(ann, state))
                    .findAny()
                    .map(
                        dataAnn ->
                            buildDescription(tree)
                                .addFix(SuggestedFix.replace(dataAnn, "@lombok.Value"))
                                .addFix(SuggestedFix.delete(builderAnn))
                                .build()))
        .orElse(Description.NO_MATCH);
  }
}
