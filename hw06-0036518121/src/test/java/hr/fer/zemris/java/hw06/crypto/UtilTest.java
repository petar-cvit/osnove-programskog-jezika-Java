package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void test1() {
		byte[] expected = new byte[] {1, -82, 34};
		byte[] actual = Util.hextobyte("01aE22");
		
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
		assertEquals(expected[2], actual[2]);
	}
	
	@Test
	void test2() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("12A"));
	}
	
	@Test
	void test3() {
		assertEquals("01ae221ab3", Util.bytetohex(new byte[] {1, -82, 34, 26, -77}));
	}
	
	@Test
	void test4() {
		byte[] c = Util.hextobyte("2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598");
		
		assertEquals("2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598", 
						Util.bytetohex(c));
	}
	
}
