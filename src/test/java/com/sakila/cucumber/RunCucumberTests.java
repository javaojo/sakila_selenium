package com.sakila.cucumber;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
        features = "src/test/resources/features",
        plugin = {"pretty"}
)

public class RunCucumberTests extends AbstractTestNGCucumberTests {
}
