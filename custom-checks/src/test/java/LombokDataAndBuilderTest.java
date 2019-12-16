import com.google.errorprone.CompilationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LombokDataAndBuilderTest {
  private final CompilationTestHelper compilationTestHelper =
      CompilationTestHelper.newInstance(LombokDataAndBuilder.class, getClass());

  @Test
  public void test() {
    compilationTestHelper
        .addSourceLines(
            "Test.java",
            "// BUG: Diagnostic contains: LombokDataAndBuilder",
            "@lombok.Data @lombok.Builder class Test {}")
        .doTest();
  }
}
