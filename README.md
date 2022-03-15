# JSONテキストをpretty printしたい --- Gsonを使って

## 解決したい問題

Java8でプログラムを自作していた。
自作のJavaクラスにtoString()メソッドを実装しようとした。
どういう形式のテキストを出力 するのがいいかと考えて、JSONテキストを出力することにした。
たとえば自作のJavaクラスがこんなテキストを出力するとしよう。

    { "key": "profile", "value": "DevEnv_1.2.3-beta", "ignoredByKey": true, "identifiedByValue": true, "semanticVersion": "1.2.3-beta"}

正しい形式のJSONになっている。しかし見づらい。このテキストをコンソールに表示しても役に立たない。
適切な改行と字下げを付け加えたい。
ただし自作Classのメソッドがいちいち改行と字下げを意識してテキストを構築するのは面倒だ。
そこでヘルパクラスを作ることにした。
任意のJSONテキストを入力として、適切な改行と字下げを加えたJSONテキストに変換する、というメソッドを実装。
つまり上記の横長なJSONテキストを入力として、下記のような形式のテキストに変換したい。

    {
      "key": "profile",
      "value": "DevEnv_1.2.3-beta",
      "ignoredByKey": true,
      "identifiedByValue": true,
      "semanticVersion": "1.2.3-beta"
    }

"Java Json pretty print"とキーワードを指定してネットを検索した。
神々がおっしゃるには、JavaでJSONを操作するコードを書くならば

[Gson](https://github.com/google/gson)

を使えという。ふむふむ。見つかった記事(
[あれ](https://www.baeldung.com/java-json)
[これ](https://qiita.com/u-chida/items/cbdd040e4199a10936dc))
を読んでわたしは困った。

それらネット記事はJavaオブジェクトとJSONテキストとの相互変換を解説していた。
JavaオブジェクトをserializeしてできたJSONテキストを入力として
Javaオブジェクトをdeserializeすることに熱中していた。

僕はそんなことしたくはない。 僕がしたいのはもっと低級なことだ。
JSON形式のStringをJSON形式のStringにする、ただし改行とインデントを加える、それだけ。
これを端的に例示してくれる記事がみつからなかった。
