package com.example.pizzaapp.util.parser

import com.example.pizzaapp.util.ListItem
import org.json.JSONObject
import java.net.URL

class DominosParser : Parser {
    override suspend fun getParsedData(): List<ListItem> {
        val height = 100
        val width = 100
        val listItems = mutableListOf<ListItem>()

        val html = URL("https://eda.yandex.by/api/v2/menu/retrieve/dominos_bevin?regionId=134&autoTranslate=false").readText()

        val jsonObject = JSONObject(html)
        val response = jsonObject.getJSONObject("payload")
        val data = response.getJSONArray("categories")

        for (i in 0 until data.length()) {
            val dataJSON = data.getJSONObject(i)
            if(dataJSON.getString("name").equals("Пиццы")){
                val littlePizzazJSON = dataJSON.getJSONArray("items")
                for(j in 0 until littlePizzazJSON.length()){
                    val littlePizzazData = littlePizzazJSON.getJSONObject(j)
                    val title = littlePizzazData.getString("name")
                    if(title.equals("Шокопицца")){
                        continue
                    }
                    val imageName = littlePizzazData.getJSONObject("picture").getString("uri")
                    val uri = "https://eda.yandex.by/" + imageName.replace("{w}", width.toString()).replace("{h}", height.toString())
                    val ingredients = littlePizzazData.getString("description")
                    val price = littlePizzazData.getString("decimalPrice").toDouble()
                    val link = "https://dominos.by/ru/minsk#Pizza"
                    val listItem = ListItem(
                        title = title,
                        imageName = uri,
                        ingredients = ingredients,
                        price = price.toString(),
                        link = link
                    )
                    listItems.add(listItem)
                }
            }
        }
        return listItems
    }
}