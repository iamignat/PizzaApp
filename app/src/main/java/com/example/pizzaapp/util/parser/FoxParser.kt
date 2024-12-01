package com.example.pizzaapp.util.parser

import com.example.pizzaapp.util.ListItem
import org.json.JSONObject
import java.net.URL

class FoxParser : Parser {
    override suspend fun getParsedData(): List<ListItem> {
        val listItems = mutableListOf<ListItem>()

        val html = URL("https://pzz.by/api/v1/pizzas?load=ingredients,filters&filter=meal_only:0&order=position:asc").readText()
        val jsonObject = JSONObject(html)
        val response = jsonObject.getJSONObject("response")
        val data = response.getJSONArray("data")
        for (i in 0 until data.length()) {
            val dataJSON = data.getJSONObject(i)
            val title = dataJSON.getString("title")
            val imageName = dataJSON.getString("photo_small")
            val ingredients = dataJSON.getString("anonce")
            val price = dataJSON.getString("medium_price").toDouble() / 10000
            val link = "https://pzz.by#pizza-" + dataJSON.getInt("id")
            val listItem = ListItem(
                title = title,
                imageName = imageName,
                ingredients = ingredients,
                price = price.toString(),
                link = link
            )
            listItems.add(listItem)
        }
        return listItems
    }
}