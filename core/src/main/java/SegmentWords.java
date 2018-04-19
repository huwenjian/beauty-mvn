import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义词性
 */
public class SegmentWords {

    public static void main(String[] args) {
        String word = "重庆市小米市饮食文化有限责任公司";
        getCoreName(word);
    }

    public static String getCoreName(String name) {
        System.out.print(name+"  ");
        String core;
        List<Term> seg = getSegment().seg(name);
        StringBuilder coreBuilder = new StringBuilder();

        for(Term s:seg){
            if (!s.nature.toString().equals("nt") && !s.nature.toString().equals("ns")&& !s.nature.toString().equals("w")&&!s.nature.toString().equals("nis")){
                coreBuilder.append(s.word);
            }
//            System.out.println(s.word+s.nature);
        }
        core = coreBuilder.toString();
        System.out.println(core);
        return core;
    }


    public static List<TermInput> segment(String word) {
        Segment segment = getSegment();
        List<TermInput> result = segment.seg(word).stream().map(eachWorld -> new TermInput(eachWorld.word, eachWorld.nature.toString(), eachWorld.offset)).collect(Collectors.toList());
        result.forEach(i -> {
            System.out.print(i.getWord() + " ");
            System.out.print(i.getNature() + " ");
        });
        System.out.println();
        return result;
    }

    private static Segment getSegment() {
        return HanLP.newSegment()
                .enableCustomDictionary(true)
                .enableOrganizationRecognize(true)
                .enablePlaceRecognize(true)
                .enableCustomDictionaryForcing(true);
    }


    private static void demo() {
        // 动态增加
        CustomDictionary.add("攻城狮");
        // 强行插入
        CustomDictionary.insert("白富美", "nz 1024");
        CustomDictionary.insert("小米科技", "n-company 1024");
        // 删除词语（注释掉试试）
//        CustomDictionary.remove("攻城狮");
        System.out.println(CustomDictionary.add("单身狗", "nz 1024 n 1"));
        System.out.println(CustomDictionary.get("单身狗"));

        String text = "攻城狮逆袭单身狗，迎娶白富美，走上人生巅峰 北京市小米科技有限公司";  // 怎么可能噗哈哈！

        // AhoCorasickDoubleArrayTrie自动机扫描文本中出现的自定义词语
        final char[] charArray = text.toCharArray();
        CustomDictionary.parseText(charArray, (begin, end, value) -> System.out.printf("[%d:%d]=%s %s\n", begin, end, new String(charArray, begin, end - begin), value));

        // 自定义词典在所有分词器中都有效
        System.out.println(HanLP.segment(text));
    }
}
