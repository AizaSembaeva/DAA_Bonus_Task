package example.algorithm;

import org.example.algorithm.KMPMatcher;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class KMPMatcherTest {
    private final KMPMatcher kmp = new KMPMatcher();
    @Test
    void testShortString() {
        String text = "hello";
        String pattern = "ll";

        List<Integer> result = kmp.findAll(text, pattern);

        assertEquals(List.of(2), result);
        assertEquals(2, kmp.findFirst(text, pattern));
    }

    @Test
    void testMediumString() {
        String text = "ababcababcababc";
        String pattern = "ababc";

        List<Integer> result = kmp.findAll(text, pattern);

        assertEquals(List.of(0, 5, 10), result);
        assertEquals(0, kmp.findFirst(text, pattern));
    }

    @Test
    void testLongString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5000; i++) {
            sb.append("abc");
        }
        String text = sb.toString();

        String pattern = "abcabc";

        List<Integer> result = kmp.findAll(text, pattern);

        assertEquals(4999, result.size());
        assertEquals(0, result.get(0));
        assertEquals((4999 - 1) * 3, result.get(result.size() - 1));
    }

    @Test
    void testNoMatch() {
        String text = "abcdefg";
        String pattern = "xyz";

        List<Integer> result = kmp.findAll(text, pattern);

        assertTrue(result.isEmpty());
        assertEquals(-1, kmp.findFirst(text, pattern));
    }

    @Test
    void testRepeatedPattern() {
        String text = "aaaaaa";
        String pattern = "aaa";

        List<Integer> result = kmp.findAll(text, pattern);

        assertEquals(List.of(0, 1, 2, 3), result);
    }
}
