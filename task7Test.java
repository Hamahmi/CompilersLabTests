import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

/*
* @author Hamahmi
* @version 1.0
* @since 2020-04-09
*/

class task7Test {

	// https://www.facebook.com/groups/1640157849618962/permalink/2264383597196381/
	private final String cfg1 = "S,iST,e;T,cS,a";
	private final String table1 = "S,$,e;S,a,e;S,c,e;S,i,iST;T,a,a;T,c,cS";
	private final String input1_1 = "iiac";
	private final String derivation1_1 = "S,iST,iiSTT,iiTT,iiaT,iiacS,iiac";
	private final String input1_2 = "iia";
	private final String derivation1_2 = "S,iST,iiSTT,iiTT,iiaT,ERROR";

	private final String cfg2 = "S,TA;A,pTA,e;T,FB;B,mFB,e;F,lSr,i";
	private final String table2 = "A,$,e;A,p,pTA;A,r,e;B,$,e;B,m,mFB;B,p,e;B,r,e;F,i,i;F,l,lSr;S,i,TA;S,l,TA;T,i,FB;T,l,FB";
	private final String input2_1 = "imlipir";
	private final String derivation2_1 = "S,TA,FBA,iBA,imFBA,imlSrBA,imlTArBA,imlFBArBA,imliBArBA,imliArBA,imlipTArBA,imlipFBArBA,imlipiBArBA,imlipiArBA,imlipirBA,imlipirA,imlipir";
	private final String input2_2 = "imlipirl";
	private final String derivation2_2 = "S,TA,FBA,iBA,imFBA,imlSrBA,imlTArBA,imlFBArBA,imliBArBA,imliArBA,imlipTArBA,imlipFBArBA,imlipiBArBA,imlipiArBA,imlipirBA,ERROR";

	private final String cfg3 = "S,zToS,e;T,zTo,e";
	private final String table3 = "S,$,e;S,z,zToS;T,o,e;T,z,zTo";
	private final String input3_1 = "zzoozo";
	private final String derivation3_1 = "S,zToS,zzTooS,zzooS,zzoozToS,zzoozoS,zzoozo";
	private final String input3_2 = "zoz";
	private final String derivation3_2 = "S,zToS,zoS,zozToS,ERROR";

	private final String cfg4 = "S,AB;A,iA,n;B,CA;C,zC,o";
	private final String table4 = "A,i,iA;A,n,n;B,o,CA;B,z,CA;C,o,o;C,z,zC;S,i,AB;S,n,AB";
	private final String input4_1 = "inzon";
	private final String derivation4_1 = "S,AB,iAB,inB,inCA,inzCA,inzoA,inzon";
	private final String input4_2 = "nzin";
	private final String derivation4_2 = "S,AB,nB,nCA,nzCA,ERROR";

	private final String cfg5 = "S,lLr,a;L,lLrD,aD;D,cSD,e";
	private final String table5 = "D,c,cSD;D,r,e;L,a,aD;L,l,lLrD;S,a,a;S,l,lLr";
	private final String input5_1 = "laclacarr";
	private final String derivation5_1 = "S,lLr,laDr,lacSDr,laclLrDr,laclaDrDr,laclacSDrDr,laclacaDrDr,laclacarDr,laclacarr";
	private final String input5_2 = "laclacarl";
	private final String derivation5_2 = "S,lLr,laDr,lacSDr,laclLrDr,laclaDrDr,laclacSDrDr,laclacaDrDr,laclacarDr,ERROR";

	private final String cfg6 = "S,aA;A,SB,e;B,pA,mA";
	private final String table6 = "A,$,e;A,a,SB;A,m,e;A,p,e;B,m,mA;B,p,pA;S,a,aA";
	private final String input6_1 = "aamaamp";
	private final String derivation6_1 = "S,aA,aSB,aaAB,aaB,aamA,aamSB,aamaAB,aamaSBB,aamaaABB,aamaaBB,aamaamAB,aamaamB,aamaampA,aamaamp";
	private final String input6_2 = "aapaap";
	private final String derivation6_2 = "S,aA,aSB,aaAB,aaB,aapA,aapSB,aapaAB,aapaSBB,aapaaABB,aapaaBB,aapaapAB,aapaapB,ERROR";

	public HashMap<Character, HashMap<Character, String>> CreateTable(String cfg, String table) {
		/**
		 * Disclaimer : Since this code is essential for task6 (so no rules were broken
		 * here :D), if you want to use this code in your task it is okay as long as you
		 * cite the author, according to this comment on this post :
		 * 
		 * [https://www.facebook.com/groups/1640157849618962/permalink/2263724007262340/]
		 * 
		 * "- can I take the first and follow from a friend? "
		 * 
		 * " = yes, but just comment at this part that you take it from a friend"
		 * 
		 * tbh I didn't want to write this, but I couldn't find anywhere in the
		 * description anything about the order, just "For convenience, entries of the
		 * same row should appear consecutively" but no order between the entries for
		 * the same variable.
		 */
		HashMap<Character, HashMap<Character, String>> outputTable = new HashMap<Character, HashMap<Character, String>>();
		ArrayList<Character> variables = new ArrayList<Character>();
		ArrayList<Character> terminals = new ArrayList<Character>();
		String[] splitted = cfg.split(";");
		for (int i = 0; i < splitted.length; i++) {
			String[] rule = splitted[i].split(",");
			variables.add(rule[0].charAt(0));
			for (int j = 1; j < rule.length; j++) {
				String sF = rule[j];
				for (int k = 0; k < sF.length(); k++) {
					char chr = sF.charAt(k);
					if (Character.isLowerCase(chr) && chr != 'e' && !terminals.contains(chr)) {
						terminals.add(chr);
					}
				}
			}
		}
		for (Character variable : variables) {
			outputTable.put(variable, new HashMap<Character, String>());
		}

		splitted = table.split(";");
		for (String str : splitted) {
			String[] entry = str.split(",");
			outputTable.get(entry[0].charAt(0)).put(entry[1].charAt(0), entry[2]);
		}
		return outputTable;
	}

	@Test
	public void TestTable1() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg1);

		assertEquals(CreateTable(cfg1, table1), CreateTable(cfg1, cfg.table()));
	}

	@Test
	public void TestInput1_1() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg1);
		cfg.table();
		assertEquals(derivation1_1, cfg.parse(input1_1));
	}

	@Test
	public void TestInput1_2() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg1);
		cfg.table();
		assertEquals(derivation1_2, cfg.parse(input1_2));
	}

	@Test
	public void TestTable2() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg2);

		assertEquals(CreateTable(cfg2, table2), CreateTable(cfg2, cfg.table()));
	}

	@Test
	public void TestInput2_1() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg2);
		cfg.table();
		assertEquals(derivation2_1, cfg.parse(input2_1));
	}

	@Test
	public void TestInput2_2() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg2);
		cfg.table();
		assertEquals(derivation2_2, cfg.parse(input2_2));
	}

	@Test
	public void TestTable3() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg3);

		assertEquals(CreateTable(cfg3, table3), CreateTable(cfg3, cfg.table()));
	}

	@Test
	public void TestInput3_1() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg3);
		cfg.table();
		assertEquals(derivation3_1, cfg.parse(input3_1));
	}

	@Test
	public void TestInput3_2() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg3);
		cfg.table();
		assertEquals(derivation3_2, cfg.parse(input3_2));
	}

	@Test
	public void TestTable4() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg4);

		assertEquals(CreateTable(cfg4, table4), CreateTable(cfg4, cfg.table()));
	}

	@Test
	public void TestInput4_1() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg4);
		cfg.table();
		assertEquals(derivation4_1, cfg.parse(input4_1));
	}

	@Test
	public void TestInput4_2() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg4);
		cfg.table();
		assertEquals(derivation4_2, cfg.parse(input4_2));
	}

	@Test
	public void TestTable5() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg5);

		assertEquals(CreateTable(cfg5, table5), CreateTable(cfg5, cfg.table()));
	}

	@Test
	public void TestInput5_1() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg5);
		cfg.table();
		assertEquals(derivation5_1, cfg.parse(input5_1));
	}

	@Test
	public void TestInput5_2() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg5);
		cfg.table();
		assertEquals(derivation5_2, cfg.parse(input5_2));
	}

	@Test
	public void TestTable6() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg6);

		assertEquals(CreateTable(cfg6, table6), CreateTable(cfg6, cfg.table()));
	}

	@Test
	public void TestInput6_1() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg6);
		cfg.table();
		assertEquals(derivation6_1, cfg.parse(input6_1));
	}

	@Test
	public void TestInput6_2() {
		Tutorial_ID_Name.CFG cfg = new Tutorial_ID_Name.CFG(cfg6);
		cfg.table();
		assertEquals(derivation6_2, cfg.parse(input6_2));
	}

}
