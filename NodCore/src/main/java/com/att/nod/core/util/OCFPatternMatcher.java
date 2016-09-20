package com.att.nod.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OCFPatternMatcher {

	private static final String BRACKET_REGEX = "\\[(.*?)\\]";
	private static final String XML_REGEX = "\\<(.*?)\\>";
	private static final String BLANK_STRING = "";
	private static final String SINGLE_SPACE = " ";
	private static final String PIPE_REGEX = "[\\d\\|]";
	private static final String SINGLE_SPACE_PATTERN = "\\s{2,}";
	private static boolean toPrint = false;

	/**
	 * This method compare 2 string and perform a substring check, also with dynamic place holder.
	 * 
	 * 
	 * @param sourceEventString This is string extracted from source system as event data
	 * @param patternString This is the pattern configurd in OCF system's config
	 * @param isCaseSensitive This says to check with case sensitive or not
	 * @return
	 */
	public static boolean performMatch(String sourceEventString,
			String patternString, boolean isCaseSensitive) throws OCFParseException {
		
		if (patternString == null || sourceEventString == null) {
			throw new IllegalArgumentException(
					"Invalid argument for pattern matching.");
		}
		
		if(patternString.equals(null) || "".equals(patternString)){
			throw new OCFParseException("Null or Invalid patternString::"+patternString);
		}
		if(sourceEventString.equals(null) || "".equals(sourceEventString)){
			throw new OCFParseException("Null or Invalid sourceEventString::"+sourceEventString);
		}

		
		boolean isMatch = false;
		sourceEventString = sourceEventString.replaceAll(SINGLE_SPACE_PATTERN, SINGLE_SPACE);
		patternString = patternString.replaceAll(SINGLE_SPACE_PATTERN, SINGLE_SPACE);
		
		String formattedPattern = refactorEquals(patternString).toString().trim();
		String formattedSource = refactorEquals(sourceEventString).toString().trim();
		
		// Case 1: Returns true if and only if this string contains the
		// specified sequence of character values.
		isMatch = checkWithCharSeq(formattedSource, formattedPattern,
				isCaseSensitive);

		// Case 2 : Exclude dynamic values having defined pattern [ and ]
		if (!isMatch) {
			isMatch = matchWithEscapingDynamicValuesSquareBracket(formattedSource,
					formattedPattern, isCaseSensitive);
		}

		// Case 3 : Exclude dynamic values having defined pattern < and >
		if (!isMatch) {
			isMatch = matchWithEscapingDynamicValuesWithXMLIndicator(formattedSource,
					formattedPattern, isCaseSensitive);
		}
		
		return isMatch;
	}
	
	private static StringBuilder refactorEquals(String sourceEventString) {
		sourceEventString = sourceEventString.replaceAll("\\s{2,}", " ");
		String[] arr = sourceEventString.split(" ");
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < arr.length; i++) {
			if (!arr[i].contains("=") || !arr[i].contains("=="))
				builder.append(arr[i]).append(SINGLE_SPACE);
		}
		return builder;
	}

	private static boolean checkWithCharSeq(String sourceEventString,
			String patternString, boolean isCaseSensitive) {
		boolean isMatch;
		
		sourceEventString=sourceEventString.replace(PIPE_REGEX, BLANK_STRING);
		patternString=patternString.replace(PIPE_REGEX, BLANK_STRING);	
		
		if (isCaseSensitive) {
			isMatch = sourceEventString
					.substring(0, sourceEventString.length()).contains(
							patternString);
		} else {
			isMatch = sourceEventString.toUpperCase()
					.substring(0, sourceEventString.length())
					.contains(patternString.toUpperCase());
		}
		return isMatch;
	}
	
	private static boolean matchWithEscapingDynamicValuesSquareBracket(
			String sourceEventString, String patternString,
			boolean isCaseSensitive) {

		int xmlPatternCount = 0;
		int xmlSourceCount = 0;

		String groupString = null;
		Pattern p = Pattern.compile(BRACKET_REGEX);
		
		StringBuffer sourceBuffer = new StringBuffer();
		StringBuffer patternBuffer = new StringBuffer();
		if(toPrint ) {
			System.out.println("before sourceEventString="+ sourceEventString);
			System.out.println("before patternString="+ patternString);
		}
		
		Matcher m = p.matcher(sourceEventString);
		while (m.find()) {
			xmlSourceCount++;
			groupString = m.group(1);
			m.appendReplacement(sourceBuffer, BLANK_STRING);
		}
		m.appendTail(sourceBuffer);
		sourceEventString = sourceBuffer.toString();
		
		m = p.matcher(patternString);
		while (m.find()) {
			xmlPatternCount++;
			groupString = m.group(1);
			m.appendReplacement(patternBuffer, BLANK_STRING);
		}
		m.appendTail(patternBuffer);
		patternString = patternBuffer.toString();

		if(toPrint ) {
			System.out.println("after sourceEventString="+ sourceEventString);
			System.out.println("after patternString="+ patternString);
		}
		
		if(xmlSourceCount == xmlPatternCount) {
			return checkWithCharSeq(sourceEventString, patternString,
					isCaseSensitive);	
		} else {
			return false;
		}

	}

	private static boolean matchWithEscapingDynamicValuesWithXMLIndicator(
			String sourceEventString, String patternString,
			boolean isCaseSensitive) {

		String groupString = null;
		Pattern p = Pattern.compile(XML_REGEX);
		int xmlPatternCount = 0;
		int xmlSourceCount = 0;
		
		StringBuffer sourceBuffer = new StringBuffer();
		StringBuffer patternBuffer = new StringBuffer();
		if(toPrint ) {
			System.out.println("before sourceEventString="+ sourceEventString);
			System.out.println("before patternString="+ patternString);
		}
		
		Matcher m = p.matcher(sourceEventString);
		while (m.find()) {
			xmlPatternCount++;
			groupString = m.group(1);
			m.appendReplacement(sourceBuffer, BLANK_STRING);
		}
		m.appendTail(sourceBuffer);
		sourceEventString = sourceBuffer.toString();
		
		m = p.matcher(patternString);
		while (m.find()) {
			xmlSourceCount++;
			groupString = m.group(1);
			m.appendReplacement(patternBuffer, BLANK_STRING);
		}
		m.appendTail(patternBuffer);
		patternString = patternBuffer.toString();

		
		if(toPrint ) {
			System.out.println("after sourceEventString="+ sourceEventString);
			System.out.println("after patternString="+ patternString);
		}
		
		if(xmlSourceCount == xmlPatternCount) {
			return checkWithCharSeq(sourceEventString, patternString,
					isCaseSensitive);	
		} else {
			return false;
		}
	}

	public static void main(String[] args)  {

		String patternString =      "99999:2nd CNL <CNL ID> can''t be provisioned as 1st CNL <SOME_ID> is not yet Provisioned";
		String  sourceEventString = "99999:2nd CNL JU102/GE1N/GRDNARAM0AW/HTSPARNA0JW can''t be provisioned as 1st CNL JU101/GE1N/GRDNARAM0AW/HTSPARNA0JW is not yet Provisioned";		//Passed, as the pattern as the pattern matches
		
		patternString = "GTA_PROCESSING_EXCEPTION - OSSLRCK - FAILED TO FIND RECORD. TIRKSMSG=Field is not valid for specified Screen - Field=EUSOC, Screen=OSSLRC";
		sourceEventString = "GTA_PROCESSING_EXCEPTION - OSSLRCK -<> FAILED TO FIND RECORD. TIRKSMSG=123 is not valid for specified Screen - Field=123, Screen=345";		
		
		patternString = "GTA - FAILED IN UPDATING WFA LINE RECORD - GTA_PROCESSING_EXCEPTION - OSSLRCK - FAILED TO FIND RECORD. TIRKSMSG=Field is not valid for specified Screen - Field=EUSOC, Screen=OSSLRC";
		sourceEventString = "GTA - FAILED IN UPDATING WFA LINE RECORD - GTA_PROCESSING_EXCEPTION - OSSLRCK -[11] FAILED TO FIND RECORD. TIRKSMSG=Field is not valid for specified Screen - Field=123, Screen=345";
		
		patternString = "EXCEPTION - OSSLRCK - FAILED TO FIND RECORD. TIRKSMSG=Field is not valid for specified Screen - Field=EUSOC, Screen=OSSLRC";
		sourceEventString = "EXCEPTION - OSSLRCK -[code=500] FAILED TO FIND RECORD. TIRKSMSG=Field is not valid for specified Screen - Field=EUSOC, Screen=OSSLRC";		
		
		patternString = "aa /a dhhhhd[txid] more text ";
		sourceEventString = "aa /a dhhhhd[] more text";
		
		try {
			System.out
					.println(performMatch(sourceEventString, patternString, false));
		} catch (OCFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
