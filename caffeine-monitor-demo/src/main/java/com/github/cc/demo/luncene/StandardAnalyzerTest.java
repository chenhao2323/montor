package com.github.cc.demo.luncene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Attribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * AnalyzerTest: ${description}
 *
 * @author chenhao
 * @version 1.0
 * @date 2021-1-16 15:58
 */
public class StandardAnalyzerTest {



   public static void main(String[] args) throws IOException {
       stdAnalyzer("中华人民共和国，有13亿人口");
   }

    public static void stdAnalyzer(String word) throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream stream = analyzer.tokenStream("test",new StringReader(word));
        stream.reset();
        Attribute arr = stream.getAttribute(CharTermAttribute.class);
        while(stream.incrementToken()){
            System.out.print(arr.toString()+"|");
        }
        analyzer.close();
    }
}
