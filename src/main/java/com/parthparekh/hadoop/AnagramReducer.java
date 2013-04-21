package com.parthparekh.hadoop;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * This reducer class will collect all the valid anagrams from mapper and writes them to a file
 *
 * @author: Parth Parekh
 */
public class AnagramReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {	
	private JobConf conf;
	private static Logger logger = Logger.getLogger(AnagramReducer.class.getName());
	
    public void configure(JobConf job) {
    	conf = job;
    }
	
	public void reduce(Text anagramKey, Iterator<Text> anagramValues,
			OutputCollector<Text, Text> results, Reporter reporter) throws IOException {
		assert conf.get("sortedAnagramWord").equals(anagramKey.toString());
		String output = "";
		while(anagramValues.hasNext()) {
			Text anagam = anagramValues.next();
			output = output + anagam.toString() + "\n";
		}
		Text reducerOutputKey = new Text();
		Text reducerOutputValue = new Text();
		reducerOutputKey.set(conf.get("sortedAnagramWord"));
		reducerOutputValue.set(output);
		results.collect(reducerOutputKey, reducerOutputValue);
		logger.info("Anagrams for word: " + conf.get("sortedAnagramWord") + " are: " + output);
	}	
}