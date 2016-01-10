/**
 * 
 */
package structures;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author hongning
 * Sentence structure for text documents
 */
public class _Stn {

	// //in parent child topic model, using m_wordIndexInDoc and m_rawTokens

	public int[] m_wordPositionInDoc;
	public double m_stnLength;
	public int[] m_words;
	public double[] m_topics;

	_SparseFeature[] m_x_sparse; // bag of words for a sentence
	
	//structure for HTMMM
	public double[] m_transitFv; // features for determine topic transition
	double m_transitStat; // posterior topic transit probability

	// tructure for LR-HTSM
	public double[] m_sentiTransitFv; // features for determine sentiment transition
	double m_sentiTransitStat; // posterior sentiment transit probability
	
	String[] m_rawTokens; // raw token sequence after tokenization
	String[] m_sentencePOSTag; // corresponding POS tags
	String m_rawSource;

	//structure for topic assignment
	int m_topic; //topic/aspect assignment
	
	//attribute label for NewEgg data
	// default is -1 so that it can help to debug 
	// 0 is for pos, 1 is for neg and 2 for neutral or comment
	// use in FastRestritedHMM.java for sentiment to decide sentiment switch 
	int m_sentimentLabel = -1;
	int m_predictedSentimentLabel = -1;
	
	public _Stn(int stnLength) {
		m_stnLength = stnLength;
		m_wordPositionInDoc = new int[stnLength];
		m_rawTokens = new String[stnLength];
		m_words = new int[stnLength]; // record index;
	}
	
	public void setTopicsVct(int k) {
		m_topics = new double[k];
	}

	public void setWordInStn(ArrayList<Integer> positionInStn,
			ArrayList<String> rawTokens, ArrayList<Integer> words) {
		// if((indexInStn.length ==
		// indexInCV.length)&&(indexInStn.length==m_stnLength)){
		for (int n = 0; n < positionInStn.size(); n++) {
			m_wordPositionInDoc[n] = positionInStn.get(n);
			m_rawTokens[n] = rawTokens.get(n);
			m_words[n] = words.get(n);
		}

	}

	public _Stn(_SparseFeature[] x, String[] rawTokens, String[] posTags, String rawSource) {
		m_x_sparse = x;
		m_rawTokens = rawTokens;
		m_sentencePOSTag = posTags;
		m_rawSource = rawSource;
		
		m_transitFv = new double[_Doc.stn_fv_size];
		m_sentiTransitFv = new double[_Doc.stn_senti_fv_size];
	}
	
	public _Stn(_SparseFeature[] x, String[] rawTokens, String[] posTags, String rawSource, int label) {
		m_x_sparse = x;
		m_rawTokens = rawTokens;
		m_sentencePOSTag = posTags;
		m_rawSource = rawSource;
		m_sentimentLabel = label;
		
		m_transitFv = new double[_Doc.stn_fv_size];
		m_sentiTransitFv = new double[_Doc.stn_senti_fv_size];
	}

	public _SparseFeature[] getFv() {
		return m_x_sparse;
	}
	
	public void setSentencePredictedSenitmentLabel(int label){
		m_predictedSentimentLabel = label;
	}
	
	public int getSentencePredictedSenitmentLabel(){
		return m_predictedSentimentLabel;
	}
	
	public void setSentenceSenitmentLabel(int label){
		m_sentimentLabel = label;
	}

	public String getRawSentence(){
		return m_rawSource;
	}
	
	public String[] getRawTokens() {
		return m_rawTokens;
	}
	
	public String[] getSentencePosTag(){
		return m_sentencePOSTag;
	}	
	
	public int getSentenceSenitmentLabel(){
		return m_sentimentLabel;
	}
	
	public double[] getTransitFvs() {
		return m_transitFv;
	}
	
	public double[] getSentiTransitFvs() {
		return m_sentiTransitFv;
	}
	
	public double getTransitStat() {
		return m_transitStat;
	}
	
	public void setTransitStat(double t) {
		m_transitStat = t;
	}
	
	public double getSentiTransitStat() {
		return m_sentiTransitStat;
	}
	
	public double getLength() {
		double length = 0;
		for(_SparseFeature f:m_x_sparse)
			length += f.getValue();
		return length;
	}
	
	public void setSentiTransitStat(double t) {
		m_sentiTransitStat = t;
	}
	
	public int getTopic() {
		return m_topic;
	}
	
	public void setTopic(int i) {
		m_topic = i;
	}
	
	//annotate by all the words
	public int AnnotateByKeyword(Set<Integer> keywords){
		int count = 0;
		for(_SparseFeature t:m_x_sparse){
			if (keywords.contains(t.getIndex()))
				count ++;
		}
		return count;
	}
}
