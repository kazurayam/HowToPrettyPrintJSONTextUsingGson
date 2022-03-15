# JSONテキストをpretty printしたい --- Gsonを使って

## 解決したい問題

Java8でプログラムを自作していた。
自作のJavaクラスにtoString()メソッドを実装しようとした。
どういう形式のテキストを出力 するのがいいかと考えて、JSONテキストを出力することにした。
たとえば自作のJavaクラスがこんなテキストを出力するとしよう。

    { "key": "profile", "value": "DevEnv_1.2.3-beta", "ignoredByKey": true, "identifiedByValue": true, "semanticVersion": "1.2.3-beta"}

正しい形式のJSONになっている。しかし見づらい。このテキストをコンソールに表示しても役に立たない。
適切な改行と字下げを付け加えたい。
任意のJSONテキストを入力として、適切な改行と字下げを加えたJSONテキストに変換する、というメソッドを作りたい。
たとえば上記の横長なJSONテキストを下記のような形式に直したい。

    {
      "key": "profile",
      "value": "DevEnv_1.2.3-beta",
      "ignoredByKey": true,
      "identifiedByValue": true,
      "semanticVersion": "1.2.3-beta"
    }

こういうテキスト変換をしてくれるヘルパを実装できればいいな。

"Java Json pretty print"とキーワードを指定してネットを検索した。
神々がおっしゃるには、JavaでJSONを操作するコードを書くならば

[Gson](https://github.com/google/gson)

を使えという。ふむふむ。 見つかった記事を
[あれ](https://www.baeldung.com/java-json)
[これ](https://qiita.com/u-chida/items/cbdd040e4199a10936dc)
読んで、わたしは困惑した。

それらネット記事はJavaオブジェクトとJSONテキストとを双方向に変換する技術を語っていた。
JavaオブジェクトをserializeしてJSONテキストにして、JSONテキストをディスクに書き込んだり
ネットワーク越しに転送してから、そのJSONテキストを入力としてJavaオブジェクトを復元する
というような記事ばかり。やれやれ。僕はそんなややこしいことをしたいわけではない。
僕がしたいのはもっと平凡なことだ。
JSON形式のStringを別のJSON形式のStringに変換したい、改行とインデントを加えたい、
それだけ。このやり方を端的に例示してくれる記事がみつからなかった。

## 解決方法

某日、ふと思いついた。たしかにGsonで解決できた。
たった3行から成るヘルパメソッドでこと足りた。これに気づくまでに何年かかったことか。

## 説明

### 入力データ

#### object.json

    { "key": "profile", "value": "DevEnv_1.2.3-beta", "ignoredByKey": true, "identifiedByValue": true, "semanticVersion": "1.2.3-beta"}

#### array.json

    [ "We are the champions, my friends", "Are we'll keep on fighting 'til the end.", "We are the champions.", "We are the champions.", "No time for losers", "'Cause we are the champions of the world."]

### pretty printするヘルパクラス

    package my;

    import com.google.gson.Gson;
    import com.google.gson.GsonBuilder;

    import java.util.Map;

    public class JsonUtil {

        static String prettyPrint(String sourceJson) {
            return prettyPrint(sourceJson, Map.class);
        }

        /**
         * The following 3 lines satisfies me
         */
        static <T> String prettyPrint(String sourceJson, Class<T> clazz) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Object obj = gson.fromJson(sourceJson, clazz);
            return gson.toJson(obj);
        }
    }

### テスト

    package my;

    import org.junit.jupiter.api.Test;

    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.List;

    public class JsonUtilTest {

        Path fixtureDir = Paths.get(".").resolve("src/test/fixture");

        @Test
        public void test_prettyPrint_object() throws IOException {
            Path file = fixtureDir.resolve("object.json");
            String jsonText = new String(Files.readAllBytes(file));
            System.out.println("---- object ----");
            System.out.println(jsonText);
            String pp = JsonUtil.prettyPrint(jsonText);
            System.out.println(pp);
        }

        @Test
        public void test_prettyPrint_array() throws IOException {
            Path file = fixtureDir.resolve("array.json");
            String jsonText = new String(Files.readAllBytes(file));
            System.out.println("---- array ----");
            System.out.println(jsonText);
            String pp = JsonUtil.prettyPrint(jsonText, List.class);
            System.out.println(pp);
        }

    }

### テストを実行すると

    > Task :compileJava
    > Task :processResources NO-SOURCE
    > Task :classes
    > Task :compileTestJava
    > Task :processTestResources NO-SOURCE
    > Task :testClasses
    > Task :test
    ---- array ----
    [ "We are the champions, my friends", "Are we'll keep on fighting 'til the end.", "We are the champions.", "We are the champions.", "No time for losers", "'Cause we are the champions of the world."]
    [
      "We are the champions, my friends",
      "Are we\u0027ll keep on fighting \u0027til the end.",
      "We are the champions.",
      "We are the champions.",
      "No time for losers",
      "\u0027Cause we are the champions of the world."
    ]
    ---- object ----
    { "key": "profile", "value": "DevEnv_1.2.3-beta", "ignoredByKey": true, "identifiedByValue": true, "semanticVersion": "1.2.3-beta"}
    {
      "key": "profile",
      "value": "DevEnv_1.2.3-beta",
      "ignoredByKey": true,
      "identifiedByValue": true,
      "semanticVersion": "1.2.3-beta"
    }
    BUILD SUCCESSFUL in 1s
    3 actionable tasks: 3 executed
    10:21:10: Task execution finished ':test --tests "my.JsonUtilTest"'.

できた。
