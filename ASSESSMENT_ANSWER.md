# Jawaban

## 1. Desain Sistem untuk layanan aplikasi antar makanan. 
Untuk dapat menyusun desain sistem, sebelumnya perlu diketahui requirement / kebutuhan fitur seperti apa yang diperlukan oleh aplikasi yang hendak dibuat. Oleh karena itu, karena requirement aplikasi tersebut tidak ditentukan sebelumnya maka saya akan menuliskan sebelumnya asumsi saya terkait requirement apa saja yang akan dibutuhkan untuk membuat aplikasi layanan antar makanan ini.

#### Requirements:
1. Penyedia rumah makan/produksi makanan dapat mendaftarkan produk mereka di aplikasi ini dengan memasukkan lokasi, nama produk, harga, status ketersediaan, dan jam operasional toko.
1. Kurir yang sudah disetujui oleh perusahaan akan diberikan akun sebagai kurir di aplikasi ini.
1. Pembeli dapat melihat toko-toko yang telah terdaftar dan melihat produk-produk yang mereka tawarkan disertai informasi lainnya yang dibutuhkan (status ketersediaan, lokasi, harga, jam operasional, dll).
1. Pembeli dapat melakukan fitur search untuk produk/toko yang diinginkan baik berdasarkan nama toko maupun nama produk.
1. Pembeli dapat memesan produk dari suatu toko dengan estimasi harga produk + ongkos kirim sudah ditampilkan sebelumnya.
1. pesanan masuk diterima oleh Toko, dan Toko dapat menerima maupun menolak pesanan. 
1. Apabila pesanan sudah siap maka pihak Toko akan mengirimkan permintaan pengantaran kepada server. 
1. Server akan mencari Kurir terdekat yang tersedia dan menetapkan Kurir tersebut menjadi yang bertanggung jawab terhadap pengiriman pesanan tersebut.

#### Pemilihan stack backend:
- Java Play! framework 

    Disamping karena Play! merupakan framework RESTFul yang cukup powerful dan flexibel, juga memudahkan dalam menangani concurrency dan proses-proses Asynchronous non-blocking I/O. Saya sudah cukup familiar dengan framework ini, sehingga tidak memerlukan learning curve yang tinggi untuk segera memulai proses development.

- MySQL sebagai Relational DB 

    Karena MySQL merupakan salah satu RDBMS yang paling banyak digunakan dan memiliki komunitas pendukung yang besar dan bisa dibilang sudah cukup dewasa sebagai sebuah RDBMS sendiri.

- Redis sebagai tools caching

    Karena Redis melakukan penyimpanan data secara "in-memory" sehingga dapat menyajikan data lebih cepat dan sesuai kebutuhan dari "cache" yang bersifat sementara dan butuh untuk dapat disajikan secara jauh lebih cepat dari data biasa.

- Elasticsearch sebagai tools search engine

    Disamping karena Elasticsearch mampu mendukung fitur full-text search yang dapat menyajikan data lebih cepat dan lebih tepat dengan fitur-fitur pendukung dari full-text search dibanding melakukan pengambilan data langsung dari database relasional, Elasticsearch juga dapat berperan sebagai NoSQL storage yang mampu menyimpan pre-processed & schemaless document, sehingga juga dapat mempercepat proses dalam penyajian data yang apabila menggunakan data langsung dari relasional DB perlu melakukan "join" dan beberapa query/proses yang cukup kompleks.

#### Desain Microservice:

Berdasarkan requirement diatas, maka dapat kita katakan bahwa dalam aplikasi ini terlibat 6 entitas, yaitu: Toko, Produk, Pembeli, Kurir, Pesanan Produk, dan Pengiriman.
Dari keenam entitas tersebut, apabila dilihat dari kedekatan relasinya maka sistem dapat dibagi menjadi 3 microservices, yaitu: Supply, Purchase, dan Shipping.

![erd](https://raw.githubusercontent.com/mrmurazza/test-binar/master/public/images/erd.jpg "ERD")

- Supply

    Bertanggungjawab terhadap entitas Toko dan Produk, dan segala proses terkait pencarian produk, 
    menyajikan informasi toko, proses registrasi toko maupun penambahan produk dan lain sebagainya.
    
- Purchase
    
    Bertanggungjawab terhadap entitas Pembali dan Pesanan, dan segala proses terkait pemesanan dan pembayaran.
    layanan ini juga akan berhubungan dengan Supply untuk mendapatkan data detail toko maupun produk, 
    dan Shipping untuk mendapatkan data kalkulasi ongkos kirim maupun data kurir.
    
- Shipping
    
    Bertanggungjawab terhadap entitas Kurir dan Pengiriman, dan segala proses terkait pembuatan pesanan pengiriman,
    penentuan Kurir, kalkulasi ongkos kirim, dan penyajian data pengiriman maupun data kurir. Layanan juga akan
    berhubungan dengan layanan supply dan purchase untuk mendapatkan data lokasi toko maupun data lokasi pembeli.   

## 2. Keamanan Pengiriman data untuk Sistem di #1 
Kemanan dalam pengiriman data antara Backend dan Mobile Apps dapat dilakukan 2 cara yaitu:

a. Pertama, memberlakukan kebutuhan Authorization dalam request-request terntentu yang hendak diamankan. Dapat ditanamkan sebagai request header ataupun cookie. Supaya data yang diberikan oleh server hanya dapat diterima oleh pengguna yang berwenang saja.

b. Kedua dan yang paling utama adalah, memastikan server sudah menggunakan protokol HTTPS. Supaya setiap request maupun response dikirim dan diterima dalam keadaan ter-enkripsi.
    
## 3. Service RESTFul API 

Service RESTFul API sudah disubmit di [Github](https://github.com/mrmurazza/test-binar)

## 4. Kekurangan pada Desain API dari Dokumen TestBinar Apiary
Ada beberapa hal menurut saya dari desain API berdasarkan dokumentasi pada https://testbinar.docs.apiary.io/ yang masih bisa ditingkatkan dan tambahkan. Diantaranya adalah:

1. Menurut saya server tidak perlu memberikan response berisikan data entry User yang baru terbuat ketika proses Sign Up, apalagi hingga mengekspos `password_digest`-nya.
1. Berdasarkan dokumentasi, format request untuk Sign up adalah menggunakan format `x-www-form-urlencoded` sedangkan yang lain menggunakan format `JSON`. menurut saya akan lebih baik untuk dibuat lebih konsisten agar tidak menimbulkan kebingungan kapan harus menggunakan `JSON` kapan menggunakan `x-www-form-urlencoded`.
1. Menurut saya, pada proses Sign up sebaiknya juga perlu melakukan konfirmasi ulang terhadap penulisan password, sehingga perlu menambahkan field baru misalnya `repeat_password` untuk memastikan data password yang hendak disimpan di database sudah benar-benar sesuai dengan apa yang diinginkan oleh user.
1. Ada beberapa bagian dokumentasi yang saya rasa kurang, yaitu:
    1. Field "status" pada response hanya dicontohkan dengan value "OK" dan tidak dituliskan field "status" ini dapat memiliki value apa saja.
    1. Seluruh contoh dokumentasi response seluruhnya adalah response sukses, sehingga penggunaan field "errors" juga tidak dicontohkan. Sehingga dapat menimbulkan kebingungan apakah "errors" ini memiliki format array of object, array of string, single string, maupun format-format lainnya.
