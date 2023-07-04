package StepDefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/Features", glue= {"StepDefinitions"},
        monochrome = true,
        plugin = {"pretty", "json:target/cucumber.json"}
//        plugin = {"pretty", "html:target/HtmlReports"}
//        plugin = {"pretty", "json:target/JSONReports/report.json"}
//        plugin = {"pretty", "junit:target/JUnitReports/report.xml"}
)
public class TestRunner {
}
