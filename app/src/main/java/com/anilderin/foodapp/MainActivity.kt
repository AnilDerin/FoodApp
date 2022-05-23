package com.anilderin.foodapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anilderin.foodapp.ui.theme.FoodAppTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    SayfaGecisleri()
                }
            }
        }
    }
}

@Composable
fun SayfaGecisleri() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa") {
        composable("anasayfa") {
            Anasayfa(navController = navController)
        }
        composable("detay_sayfa/{yemek}", arguments = listOf(
            navArgument("yemek") { type = NavType.StringType }
        )) {
            val json = it.arguments?.getString("yemek")
            val yemek = Gson().fromJson(json, Yemekler::class.java)
            DetailScreen(yemek = yemek)
        }
    }

}

@Composable
fun Anasayfa(navController: NavController) {
    val yemeklerListesi = remember { mutableStateListOf<Yemekler>() }

    LaunchedEffect(key1 = true) {
        val y1 =
            Yemekler(
                yemek_id = 1,
                yemek_adi = "Köfte",
                yemek_resim_adi = "kofte",
                yemek_fiyat = 15
            )
        val y2 =
            Yemekler(
                yemek_id = 2,
                yemek_adi = "Ayran",
                yemek_resim_adi = "ayran",
                yemek_fiyat = 2
            )
        val y3 =
            Yemekler(
                yemek_id = 3,
                yemek_adi = "Fanta",
                yemek_resim_adi = "fanta",
                yemek_fiyat = 3
            )
        val y4 =
            Yemekler(yemek_id = 4,
                yemek_adi = "Makarna",
                yemek_resim_adi = "makarna",
                yemek_fiyat = 14)
        val y5 =
            Yemekler(yemek_id = 5,
                yemek_adi = "Kadayıf",
                yemek_resim_adi = "kadayif",
                yemek_fiyat = 8)
        val y6 =
            Yemekler(yemek_id = 6,
                yemek_adi = "Baklava",
                yemek_resim_adi = "baklava",
                yemek_fiyat = 15)

        yemeklerListesi.add(y1)
        yemeklerListesi.add(y2)
        yemeklerListesi.add(y3)
        yemeklerListesi.add(y4)
        yemeklerListesi.add(y5)
        yemeklerListesi.add(y6)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Yemekler") },
                backgroundColor = colorResource(id = R.color.anaRenk),
                contentColor = colorResource(id = R.color.white)
            )
        },
        content = {

            LazyColumn {
                items(
                    count = yemeklerListesi.count(),
                    itemContent = {
                        val yemek = yemeklerListesi[it]
                        Card(modifier = Modifier
                            .padding(all = 5.dp)
                            .fillMaxWidth()) {
                            Row(modifier = Modifier.clickable {
                                val yemekJson = Gson().toJson(yemek)
                                navController.navigate("detay_sayfa/${yemekJson}")
                            })
                            {
                                Row(verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(all = 10.dp)
                                        .fillMaxWidth()
                                ) {
                                    val activity = (LocalContext.current as Activity)
                                    Image(bitmap = ImageBitmap.imageResource(id = activity.resources.getIdentifier(
                                        yemek.yemek_resim_adi, "drawable", activity.packageName
                                    )), contentDescription = "", modifier = Modifier.size(100.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(verticalArrangement = Arrangement.SpaceEvenly,
                                            modifier = Modifier.fillMaxHeight()
                                        ) {
                                            Text(text = yemek.yemek_adi, fontSize = 20.sp)
                                            Spacer(modifier = Modifier.size(25.dp))
                                            Text(text = "${yemek.yemek_fiyat} ₺",
                                                color = Color.Blue)
                                        }
                                        Icon(painter = painterResource(id = R.drawable.arrow_resim),
                                            contentDescription = "")
                                    }
                                }

                            }
                        }
                    }
                )
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodAppTheme {

    }
}