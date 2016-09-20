/*package com.att.nod.core.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OCFPatternMatcherTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPerformMatch1() {
		testTIRKSPostCircuitInteractionsFailure();
	}
	@Test
	public void testPerformMatch2() {
		testTIRKSCreateRouteAndCircuitInteractionsFailure();
	}
	@Test
	public void testPerformMatch3() {
		testTIRKSCreateRouteAndCircuitInteractionsFailure_case1();
	}
	@Test
	public void testPerformMatch4() {
		testWFAAndNSDBLineRecordUpdateFailure();
	}
	@Test
	public void testPerformMatch5() {
		testOSSCNFAILFORMOBILITY();
	}
	@Test
	public void testPerformMatch6() {
		testIDISProvisioningRequestFailure();
	}
	@Test
	public void testPerformMatch7() {
		testIDISProvisioningRequestFailure_case2();
	}
	@Test
	public void testPerformMatch8() {
		testIDISProvisioningRequestFailure_case3();
	}
	@Test
	public void testPerformMatch9() {
		testIDISProvisioningRequestFailure_case4();
	}
	
	
	
	private void testIDISProvisioningRequestFailure_case4() {
		String patternString = "AAF Diversity not supported on the NTE model CN3903x. NoD does not support AAF diversity";
		String sourceEventString = "text before AAF Diversity not supported on the NTE model CN3903x. NoD does not support AAF diversity text after";
		//Passed as the pattern configured is capital and the API parameter is set to case-sensitive
		try {
			assertEquals(true, OCFPatternMatcher.performMatch(sourceEventString, patternString, false));
		} catch (OCFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//the placeholder value need to be configured unique in the pattern string
	private void testIDISProvisioningRequestFailure_case3() {
		String patternString = "32502:EMUX CLLI <CLLI11> cannot support the request CIR <CIR_NUM> as its going beyond EMUX Threshold Limit 500.";
		String sourceEventString = "text before 32502:EMUX CLLI <12sadsad> cannot support the request CIR <123> as its going beyond EMUX Threshold Limit 500. text after";
		//Passed as the pattern configured is capital and the API parameter is set to case-sensitive
		try {
			assertEquals(true, OCFPatternMatcher.performMatch(sourceEventString, patternString, false));
		} catch (OCFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testIDISProvisioningRequestFailure_case2() {
		String patternString = "32540:Service cannot be supported because CNL1 <CNL CLFI> does not have the requested capacity 1000 as current CNL utilization is 510 CNL Capacity is 1,000 and NTE <DEVICE ID> does not support more than one";
		String sourceEventString = "text before 32540:Service cannot be supported because CNL1 <6666666,9999> does not have the requested capacity 1000 as current CNL utilization is 510 CNL Capacity is 1,000 and NTE <12345> does not support more than one text after";
		//Passed as the pattern configured is capital and the API parameter is set to case-sensitive
		try {
			assertEquals(true, OCFPatternMatcher.performMatch(sourceEventString, patternString, false));
		} catch (OCFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testIDISProvisioningRequestFailure() {
		String patternString = "900009:Error No Port(s) Available On Device [  <DEVICE ID>  ]";
		String sourceEventString = "900009:Error No Port(s) Available On Device []";
		//Passed as the pattern configured is capital and the API parameter is set to case-sensitive
		try {
			assertEquals(true, OCFPatternMatcher.performMatch(sourceEventString, patternString, false));
		} catch (OCFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testOSSCNFAILFORMOBILITY() {
		String patternString = "GTA POST OSSCN OPERATION FAILED - Field is not valid for specified Screen - Field=PAGE_NUM, Screen=OSSLRCK";
		String sourceEventString = "some text before GTA POST OSSCN OPERATION FAILED - Field is not valid for specified Screen - Field=1234, Screen=screen1 some text after";
		//Passed as the pattern configured is capital and the API parameter is set to case-sensitive
		try {
			assertEquals(true, OCFPatternMatcher.performMatch(sourceEventString, patternString, false));
		} catch (OCFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testWFAAndNSDBLineRecordUpdateFailure() {
		String patternString = "GTA - FAILED IN UPDATING WFA LINE RECORD - GTA_PROCESSING_EXCEPTION - OSSLRCK - FAILED TO FIND RECORD. TIRKSMSG=Field is not valid for specified Screen - Field=EUSOC, Screen=OSSLRC";
		String sourceEventString = "some text before GTA - FAILED IN UPDATING WFA LINE RECORD - GTA_PROCESSING_EXCEPTION - OSSLRCK - FAILED TO FIND RECORD. TIRKSMSG=123 is not valid for specified Screen - Field=123414, Screen=OSSLRC some text after";
		//Passed as the pattern configured is capital and the API parameter is set to case-sensitive
		try {
			assertEquals(true, OCFPatternMatcher.performMatch(sourceEventString, patternString, false));
		} catch (OCFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testTIRKSCreateRouteAndCircuitInteractionsFailure_case1() {
		String patternString = "GTA/TIRKS responded with error TIRKS Create Circuit Transaction Failed with error =Exception while processing Create Circuit Request - MAINFRAME DATA EXCEPTION - LOC504E FIND FAILED LOCATION CODE NOT FOUND - [LOCATION CODE=SNMTCA850AW|||]. Contact Application Support.";
		String sourceEventString = "some text before GTA/TIRKS responded with error TIRKS Create Circuit Transaction Failed with error =Exception while processing Create Circuit Request - MAINFRAME DATA EXCEPTION - LOC504E FIND FAILED LOCATION CODE NOT FOUND - [LOCATION CODE=SNMTCA850AW|123|xyz|abc]. Contact Application Support. some text after";
		//Passed as the pattern configured is capital and the API parameter is set to case-sensitive
		try {
			assertEquals(true, OCFPatternMatcher.performMatch(sourceEventString, patternString, false));
		} catch (OCFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testTIRKSCreateRouteAndCircuitInteractionsFailure() {
		String patternString = "GTA/TIRKS responded with error TIRKS Create Circuit Transaction Failed with error =GTA - FAILURE DURING SUPP PROCESSING, ERROR MSG=MAINFRAME DATA EXCEPTION - GOC0894E ACTION NOT ALLOWED ON THIS SCREEN. Contact Application Support.";
		String sourceEventString = "Some issue in GTA/TIRKS responded with error TIRKS Create Circuit Transaction Failed with error =GTA - FAILURE DURING SUPP PROCESSING, ERROR MSG=MAINFRAME DATA EXCEPTION - GOC0894E ACTION NOT ALLOWED ON THIS SCREEN. Contact Application Support. Contact immediately";
		//Passed as the pattern configured is capital and the API parameter is set to case-sensitive
		try {
			assertEquals(true, OCFPatternMatcher.performMatch(sourceEventString, patternString, false));
		} catch (OCFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void testTIRKSPostCircuitInteractionsFailure() {
		String patternString = "GTA/TIRKS responded with error TIRKS Post Circuit Transaction Failed with error =CD - CIRCUIT POST FAILED - MAINFRAME DATA EXCEPTION - FEP456S FEPASN FAILED ALL CIRCUITS ASSIGNED - [TP=NI|NX|NC|N|N|N|S|NI|NX|||||||||]. Contact Application Support.";
		String sourceEventString = "Some issue in GTA/TIRKS responded with error TIRKS Post Circuit Transaction Failed with error =CD - CIRCUIT POST FAILED - MAINFRAME DATA EXCEPTION - FEP456S FEPASN FAILED ALL CIRCUITS ASSIGNED - [TP=NI|NX|NC|N|N|N|S|NI|NX|||||||||]. Contact Application Support., please check";
		//Passed as the pattern configured is capital and the API parameter is set to case-sensitive
		try {
			assertEquals(true, OCFPatternMatcher.performMatch(sourceEventString, patternString, false));
		} catch (OCFParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
*/