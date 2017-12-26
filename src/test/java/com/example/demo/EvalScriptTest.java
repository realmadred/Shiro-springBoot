package com.example.demo;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class EvalScriptTest {

    @Test
    public void test() {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine se = manager.getEngineByName("js");
            final int i = 1024;
            String str = " a >= 100 && a <= 1000";
            System.out.println(se.eval(str.replaceAll("a",Integer.toString(i))));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
