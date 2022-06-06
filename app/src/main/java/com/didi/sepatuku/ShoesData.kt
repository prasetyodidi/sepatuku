package com.didi.sepatuku

class ShoesData {
    private val shoesName = arrayOf(
        "Patrobas equip low black",
        "Patrobas equip high black",
        "Patrobas equip low maroon",
        "Patrobas equip high maroon",
        "Patrobas equip low charcoal",
        "Patrobas equip high charcoal",
        "Patrobas ivan low black",
        "Patrobas ivan high black",
        "Patrobas ivan low maroon",
        "Patrobas ivan high maroon",
        "Patrobas hugo low all black",
        "Patrobas hugo high all black",
    )

    private val description = mapOf(
        "equip" to "EQUIP merupakan kombinasi dari ciri khas kedua model sepatu Patrobas, yaitu Ivan dan Hawk. Disini, kami mengadopsi bumper belakang bermotif gerigi yang sudah menjadi ciri khas dari artikel Hawk sejak tahun 2018. Kemudian pemilihan warna logo stripe Patrob",
        "ivan" to "IVAN series adalah sepatu yang sudah melalui proses penyegaran dengan menambahkan upper logo “wavy” sebagai brand identity Patrobas. Sepatu ini dirancang dengan konstruksi Vulcanized dan sudah melalui proses suhu uap yang tinggi, jadi tidak perlu diragukan",
        "hugo" to "ISAAC di desain khusus buat kamu yang suka banget tampil dengan style modern casual. Dirancang dengan material berkualitas tinggi (Kulit Suede Asli, Kanvas, Sol Karet) yang pas banget kamu pake sehari-hari dengan situasi apapun dan dimanapun."
    )

    private val shoesImage = intArrayOf(
        R.drawable.patrobas_equip_low_black,
        R.drawable.patrobas_equip_high_black,
        R.drawable.patrobas_equip_low_maroon,
        R.drawable.patrobas_equip_high_maroon,
        R.drawable.patrobas_equip_low_charcoal,
        R.drawable.patrobas_equip_high_charcoal,
        R.drawable.patrobas_ivan_low_black,
        R.drawable.patrobas_ivan_high_black,
        R.drawable.patrobas_ivan_low_maroon,
        R.drawable.patrobas_ivan_high_maroon,
        R.drawable.patrobas_hugo_low_all_black,
        R.drawable.patrobas_hugo_high_all_black
    )

    val listData: ArrayList<Shoes>
        get() {
            val list = ArrayList<Shoes>()
            for (postition in shoesName.indices){
                val shoes = Shoes()
                shoes.name = shoesName[postition]
                shoes.photo= shoesImage[postition]
                shoes.price = if (shoesName[postition].contains("low")){
                    239000
                }else{
                    279000
                }
                shoes.sizes = if (postition % 3 == 1){
                    listOf(36, 37, 38, 39)
                }else {
                    listOf(40, 41, 42, 43)
                }
                when {
                    shoesName[postition].contains("equip") -> {
                        shoes.description = description["equip"].toString()
                    }
                    shoesName[postition].contains("ivan") -> {
                        shoes.description = description["ivan"].toString()
                    }
                    else -> {
                        shoes.description = description["hugo"].toString()
                    }
                }
                shoes.stock = (1..10).random()
                list.add(shoes)
            }
            return list
        }
}
