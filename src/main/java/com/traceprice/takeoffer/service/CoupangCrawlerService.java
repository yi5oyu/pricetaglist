package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.Repository.*;
import com.traceprice.takeoffer.entity.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.sql.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CoupangCrawlerService implements CrawlerService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private VenderRepository venderRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private ProductInfoByDateRepository productInfoByDateRepository;

    @Override
    @Async
    public CompletableFuture<Void> getSearchResults() throws IOException, InterruptedException {

        Date currentDate = new Date(System.currentTimeMillis());
        List<String> query = new ArrayList<>(List.of(
            "마우스","키보드"
        ));
        List<String> ban = new ArrayList<>(List.of(
                "장갑","어린이","스트랩","중고","필름","가방","파우치","쿨러","거치대","마우스패드","백팩","미개봉",
                "가개통","시크릿쥬쥬","미미월드","잠금장치","클린스킨","케이블","청소","장패드","마우스 패드","케이스","쿠션",
                "커버","마우스패치","RIPJAWS","바코드스캐너","모니터암","분실방지","에어태그","케이스","키링","밴드","루프",
                "카드지갑","리모컨","광택용","젠더","Adapter","컨버터","마그네틱","팁스","줄꼬임방지","대림대교수",
                "스티커","키캡","워시","보호패드","무소음","저소음"
        ));

        for(String q : query){
            int count = 1;

            while (true){
            Random random = new Random();
            int randomTimeout = random.nextInt(1) + 1;
            TimeUnit.SECONDS.sleep(randomTimeout);
            String url = "https://www.coupang.com/np/search?rocketAll=true&listSize=72&component=&q=" + q + "&page=" + count++;

            Document d = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                    .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                    .get();
            Elements productElements = d.select(".search-product");

                for (Element productEl : productElements) {
                    String name = productEl.select(".name").text();
                    String price = productEl.select(".price-value").text();
                    String address = "https://www.coupang.com";
                    address += productEl.select(".search-product-link").attr("href");
                    Pattern pattern = Pattern.compile("products/(\\d+)\\?itemId=(\\d+)&vendorItemId=(\\d+)");
                    Matcher matcher = pattern.matcher(address);
                    Long product_number = 0L;
                    Long item_number = 0L;
                    Long vender_number = 0L;
                    if(matcher.find()){
                        product_number = Long.parseLong(matcher.group(1));
                        item_number = Long.parseLong(matcher.group(2));
                        vender_number = Long.parseLong(matcher.group(3));

                    }
                    String out = productEl.select(".out-of-stock").text();
                    Boolean ban_list = ban.stream().noneMatch(name::contains);

                    if(!price.isEmpty() && out.isEmpty() && ban_list){
                        randomTimeout = random.nextInt(3) + 1;
                        TimeUnit.SECONDS.sleep(randomTimeout);
                        Document p = Jsoup.connect(address)
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                                .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                                .get();
                        String img = p.select(".prod-image__detail").attr("src");
                        System.out.println(randomTimeout +" : "+ img);
                        String full_name = p.select(".prod-buy-header__title").text();
                        String detail_info = p.select(".prod-buy-header__attribute-title").text();
                        String pname = "";
                        if(!detail_info.isEmpty()){
                            pname = full_name.replace(detail_info, "").trim();
                            detail_info = detail_info.replaceAll(" · " , ",");
                            System.out.println(detail_info);
                        } else
                            pname = full_name;
                        System.out.println(pname + "-- "+ detail_info);
                        Long fixed_price = 0L;
                        String orign_price = p.select(".origin-price").text();
                        if(!orign_price.isEmpty())
                            fixed_price = Long.parseLong(orign_price.replaceAll("[^0-9]", ""));
                        System.out.println("fixed : " + fixed_price);
                        int discount_rate = 0;
                        String discount = p.select(".discount-rate").text();
                        if(!discount.isEmpty())
                            discount_rate = Integer.parseInt(discount.replaceAll("[^0-9]", ""));
                        System.out.println("dis : "+discount_rate);
                        Long daily_price = 0L;
                        String total_price ="";
                        Element el_total_price = p.select(".total-price > strong").last();

                        if(el_total_price != null){
                            total_price = el_total_price.text();
                            daily_price = Long.parseLong(total_price.replaceAll("[^0-9]", ""));
                        }
                        System.out.println(daily_price);
                        String item_quantity = p.select(".aos-label").text();
                        String out_of_stock = p.select(".oos-label").text();
                        if(!out_of_stock.isEmpty())
                            item_quantity = "품절";

                        String delivery_type = "";
                        delivery_type = p.select(".badge.rocket > img").attr("src");
                        if(delivery_type.equals("https://image6.coupangcdn.com/image/cmg/icon/ios/logo_rocket_large@3x.png")){
                            delivery_type = "로켓배송";
                        } else if(delivery_type.equals("https://image7.coupangcdn.com/image/coupang/rds/logo/iphone_2x/logoRocketMerchantLargeV3R3@2x.png")){
                            delivery_type = "판매자로켓";
                        }

                        if (!pname.equals("")) {

                            Product product = productRepository.findByProductNumber(product_number)
                                    .orElse(Product.builder()
                                            .marketName("쿠팡")
                                            .productType(q)
                                            .productNumber(product_number)
                                            .build());

                            productRepository.save(product);

                            Item item = itemRepository.findByItemNumber(item_number)
                                    .orElse(Item.builder()
                                            .product(product)
                                            .itemNumber(item_number)
                                            .pname(pname)
                                            .itemImg(img)
                                            .build());
                            itemRepository.save(item);

                            VenderItem venderItem = venderRepository.findByVenderNumber(vender_number)
                                    .orElse(VenderItem.builder()
                                            .item(item)
                                            .fixedPrice(fixed_price)
                                            .detailInfo(detail_info)
                                            .venderNumber(vender_number)
                                            .build());
                            venderRepository.save(venderItem);

                            Delivery delivery = deliveryRepository.findByVenderItemId(venderItem.getId())
                                    .orElse(Delivery.builder()
                                            .venderItem(venderItem)
                                            .deliveryFee(0)
                                            .deliveryType(delivery_type)
                                            .build());
                            deliveryRepository.save(delivery);

                            ProductInfoByDate productInfoByDate;
                            boolean productInfoByDateExists = productInfoByDateRepository.existsByVenderItemId(venderItem.getId());
                            if(!productInfoByDateExists){
                                productInfoByDate = ProductInfoByDate.builder()
                                        .venderItem(venderItem)
                                        .dailyPrice(daily_price)
                                        .discountRate(discount_rate)
                                        .itemQuantity(item_quantity)
                                        .priceDate(currentDate)
                                        .build();
                                productInfoByDateRepository.save(productInfoByDate);
                            } else {
                                Long l = productInfoByDateRepository.countByPriceDateAndVenderItemId(currentDate, venderItem.getId());
                                if(l == 0){
                                    productInfoByDate = ProductInfoByDate.builder()
                                            .venderItem(venderItem)
                                            .dailyPrice(daily_price)
                                            .discountRate(discount_rate)
                                            .itemQuantity(item_quantity)
                                            .priceDate(currentDate)
                                            .build();
                                    productInfoByDateRepository.save(productInfoByDate);
                                }
                            }
                        }
                    }
                }
                Elements btn = d.select(".btn-next");
                if (btn.size() > 0) {
                Boolean b = btn.get(0).attr("class").contains("disabled");
                if (b)
                    break;
            }
            }
        }
    return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<Void> getAppleResults() throws IOException, InterruptedException {
        int count = 1;
        Date currentDate = new Date(System.currentTimeMillis());
        List<String> ban = new ArrayList<>(List.of(
                "장갑","어린이","스트랩","중고","필름","가방","파우치","쿨러","거치대","마우스패드","백팩","미개봉",
                "가개통","시크릿쥬쥬","미미월드","잠금장치","클린스킨","케이블","청소","장패드","마우스 패드","케이스","쿠션",
                "커버","마우스패치","RIPJAWS","바코드스캐너","모니터암","분실방지","에어태그","케이스","키링","밴드","루프",
                "카드지갑","리모컨","광택용","젠더","Adapter","컨버터","마그네틱","팁스","줄꼬임방지","대림대교수",
                "스티커","키캡","워시","보호패드","무소음","저소음"
        ));
        while (true) {
            Random random = new Random();
            int randomTimeout = random.nextInt(1) + 1;
            TimeUnit.SECONDS.sleep(randomTimeout);
            String url = "https://www.coupang.com/np/products/brand-shop?brandName=Apple&page=" + count;
            Document d = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                    .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                    .get();
            count++;
            Elements productElements = d.select(".baby-product");
            for (Element productEl : productElements) {
                String name = productEl.select(".name").text();
                String price = productEl.select(".price-value").text();
                System.err.print(price +" ");
                String address = "https://www.coupang.com" + productEl.select(".baby-product-link").attr("href");
                System.err.print(address +" ");
                Pattern pattern = Pattern.compile("products/(\\d+)\\?itemId=(\\d+)&vendorItemId=(\\d+)");
                Matcher matcher = pattern.matcher(address);
                Long product_number = 0L;
                Long item_number = 0L;
                Long vender_number = 0L;
                if (matcher.find()) {
                    product_number = Long.parseLong(matcher.group(1));
                    item_number = Long.parseLong(matcher.group(2));
                    vender_number = Long.parseLong(matcher.group(3));
                }
                System.err.print(product_number +" " + item_number + " ");
                Boolean ban_list = ban.stream().noneMatch(name::contains);

                if (!price.isEmpty() && ban_list) {
                    randomTimeout = random.nextInt(3) + 1;
                    TimeUnit.SECONDS.sleep(randomTimeout);
                    Document p = Jsoup.connect(address)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                            .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                            .get();
                    String img = p.select(".prod-image__detail").attr("src");
                    System.out.println(randomTimeout + " : " + img);
                    String full_name = p.select(".prod-buy-header__title").text();
                    String detail_info = p.select(".prod-buy-header__attribute-title").text();
                    String pname = "";
                    if (!detail_info.isEmpty()) {
                        pname = full_name.replace(detail_info, "").trim();
                        detail_info = detail_info.replaceAll(" · ", ",");
                        System.out.println(detail_info);
                    } else
                        pname = full_name;
                    System.out.println(pname + "-- " + detail_info);
                    Long fixed_price = 0L;
                    String orign_price = p.select(".origin-price").text();
                    if (!orign_price.isEmpty())
                        fixed_price = Long.parseLong(orign_price.replaceAll("[^0-9]", ""));
                    System.out.println("fixed : " + fixed_price);
                    int discount_rate = 0;
                    String discount = p.select(".discount-rate").text();
                    if (!discount.isEmpty())
                        discount_rate = Integer.parseInt(discount.replaceAll("[^0-9]", ""));
                    System.out.println("dis : " + discount_rate);
                    Long daily_price = 0L;
                    String total_price = "";
                    Element el_total_price = p.select(".total-price > strong").last();

                    if (el_total_price != null) {
                        total_price = el_total_price.text();
                        daily_price = Long.parseLong(total_price.replaceAll("[^0-9]", ""));
                    }
                    System.out.println(daily_price);
                    String item_quantity = p.select(".aos-label").text();
                    String out_of_stock = p.select(".oos-label").text();
                    if (!out_of_stock.isEmpty())
                        item_quantity = "품절";

                    String delivery_type = "";
                    delivery_type = p.select(".badge.rocket > img").attr("src");
                    if (delivery_type.equals("https://image6.coupangcdn.com/image/cmg/icon/ios/logo_rocket_large@3x.png")) {
                        delivery_type = "로켓배송";
                    }

                    if (!pname.equals("")) {

                        Product product = productRepository.findByProductNumber(product_number)
                                .orElse(Product.builder()
                                        .marketName("쿠팡")
                                        .productType("apple")
                                        .productNumber(product_number)
                                        .build());

                        productRepository.save(product);

                        Item item = itemRepository.findByItemNumber(item_number)
                                .orElse(Item.builder()
                                        .product(product)
                                        .itemNumber(item_number)
                                        .pname(pname)
                                        .itemImg(img)
                                        .build());
                        itemRepository.save(item);

                        VenderItem venderItem = venderRepository.findByVenderNumber(vender_number)
                                .orElse(VenderItem.builder()
                                        .item(item)
                                        .fixedPrice(fixed_price)
                                        .detailInfo(detail_info)
                                        .venderNumber(vender_number)
                                        .build());
                        venderRepository.save(venderItem);

                        Delivery delivery = deliveryRepository.findByVenderItemId(venderItem.getId())
                                .orElse(Delivery.builder()
                                        .venderItem(venderItem)
                                        .deliveryFee(0)
                                        .deliveryType(delivery_type)
                                        .build());
                        deliveryRepository.save(delivery);

                        ProductInfoByDate productInfoByDate;
                        boolean productInfoByDateExists = productInfoByDateRepository.existsByVenderItemId(venderItem.getId());
                        if(!productInfoByDateExists){
                            productInfoByDate = ProductInfoByDate.builder()
                                    .venderItem(venderItem)
                                    .dailyPrice(daily_price)
                                    .discountRate(discount_rate)
                                    .itemQuantity(item_quantity)
                                    .priceDate(currentDate)
                                    .build();
                            productInfoByDateRepository.save(productInfoByDate);
                        } else {
                            Long l = productInfoByDateRepository.countByPriceDateAndVenderItemId(currentDate, venderItem.getId());
                            if(l == 0){
                                productInfoByDate = ProductInfoByDate.builder()
                                        .venderItem(venderItem)
                                        .dailyPrice(daily_price)
                                        .discountRate(discount_rate)
                                        .itemQuantity(item_quantity)
                                        .priceDate(currentDate)
                                        .build();
                                productInfoByDateRepository.save(productInfoByDate);
                            }
                        }
                    }
                }
            }
            Elements btn = d.select(".no-list-item");
            if(!btn.isEmpty()) break;
        }
        System.out.println("카운트 : "+ count);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<Void> categoryResults() throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        Map<String, String> productMap = new HashMap<>();
        productMap.put("TV", "178456");
        productMap.put("휴대폰", "497244");
        productMap.put("태블릿PC", "497246");
        productMap.put("노트북", "497135");
        productMap.put("모니터", "510541");
        productMap.put("데스크탑", "497136");
        productMap.put("스마트워치", "497253");
        productMap.put("이어폰", "178401");
        productMap.put("헤드폰", "178405");
        productMap.put("마우스", "520236");
        productMap.put("키보드", "520239");
        List<String> ban = new ArrayList<>(List.of(
                "장갑","어린이","스트랩","중고","필름","가방","파우치","쿨러","거치대","마우스패드","백팩","미개봉",
                "가개통","시크릿쥬쥬","미미월드","잠금장치","클린스킨","케이블","청소","장패드","마우스 패드","케이스","쿠션",
                "커버","마우스패치","RIPJAWS","바코드스캐너","모니터암","분실방지","에어태그","케이스","키링","밴드","루프",
                "카드지갑","리모컨","광택용","젠더","Adapter","컨버터","마그네틱","팁스","줄꼬임방지","대림대교수",
                "스티커","키캡","워시","보호패드","무소음","저소음"
        ));
//"케이스",
        for (Map.Entry<String, String> entry : productMap.entrySet()) {
            int count = 1;
            Date currentDate = new Date(System.currentTimeMillis());
            String q = entry.getKey();
            String type = entry.getValue();
            System.err.println("시작: "+q);

            while (true) {
                Random random = new Random();
                int randomTimeout = random.nextInt(1) + 1;
                TimeUnit.SECONDS.sleep(randomTimeout);
                String url = "https://www.coupang.com/np/categories/"+type+"?listSize=120&filterType=rocket&page=" + count++;
                Document d = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                        .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                        .get();
                Elements productElements = d.select(".baby-product");

                for (Element productEl : productElements) {
                    String name = productEl.select(".name").text();
                    String price = productEl.select(".price-value").text();
                    String address = "https://www.coupang.com" + productEl.select(".baby-product-link").attr("href");
                    System.err.println(address);
                    Pattern pattern = Pattern.compile("products/(\\d+)\\?itemId=(\\d+)&vendorItemId=(\\d+)");
                    Matcher matcher = pattern.matcher(address);
                    Long product_number = 0L;
                    Long item_number = 0L;
                    Long vender_number = 0L;
                    if (matcher.find()) {
                        product_number = Long.parseLong(matcher.group(1));
                        item_number = Long.parseLong(matcher.group(2));
                        vender_number = Long.parseLong(matcher.group(3));
                    }
                    String out = productEl.select(".out-of-stock").text();
                    System.out.println("품절여부: " + out);
                    Boolean ban_list = ban.stream().noneMatch(name::contains);

                    if (!price.isEmpty() && out.isEmpty() && ban_list) {
                        randomTimeout = random.nextInt(3) + 1;
                        TimeUnit.SECONDS.sleep(randomTimeout);
                        Document p = Jsoup.connect(address)
                                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                                .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                                .get();
                        String img = p.select(".prod-image__detail").attr("src");
                        String full_name = p.select(".prod-buy-header__title").text();
                        String detail_info = p.select(".prod-buy-header__attribute-title").text();
                        String pname = "";
                        if (!detail_info.isEmpty()) {
                            pname = full_name.replace(detail_info, "").trim();
                            detail_info = detail_info.replaceAll(" · ", ",");
                        } else
                            pname = full_name;
                        Long fixed_price = 0L;
                        String orign_price = p.select(".origin-price").text();
                        if (!orign_price.isEmpty())
                            fixed_price = Long.parseLong(orign_price.replaceAll("[^0-9]", ""));
                        int discount_rate = 0;
                        String discount = p.select(".discount-rate").text();
                        if (!discount.isEmpty())
                            discount_rate = Integer.parseInt(discount.replaceAll("[^0-9]", ""));
                        Long daily_price = 0L;
                        String total_price = "";
                        Element el_total_price = p.select(".total-price > strong").last();

                        if (el_total_price != null) {
                            total_price = el_total_price.text();
                            daily_price = Long.parseLong(total_price.replaceAll("[^0-9]", ""));
                        }
                        String item_quantity = p.select(".aos-label").text();
                        String out_of_stock = p.select(".oos-label").text();
                        if (!out_of_stock.isEmpty())
                            item_quantity = "품절";

                        String delivery_type = "";
                        String deliType = p.select(".td-delivery-badge > img").attr("src");
                        if (deliType.equals("//image10.coupangcdn.com/image/badges/rocket/rocket_logo.png")) {
                            delivery_type = "로켓배송";
                        }else if(deliType.equals("//image7.coupangcdn.com/image/coupang/rds/logo/iphone_2x/logoRocketMerchantLargeV3R3@2x.png")){
                            delivery_type = "판매자로켓";
                        }else if(deliType.equals("//image7.coupangcdn.com/image/badges/rocket-install/v3/aos_2/rocket_install_xhdpi.png")){
                            delivery_type = "로켓설치";
                        }
                        System.err.println("로켓: " + delivery_type +" : " + randomTimeout);

                        if (!pname.equals("")) {

                            Product product = productRepository.findByProductNumber(product_number)
                                    .orElse(Product.builder()
                                            .marketName("쿠팡")
                                            .productType(q)
                                            .productNumber(product_number)
                                            .build());

                            productRepository.save(product);

                            Item item = itemRepository.findByItemNumber(item_number)
                                    .orElse(Item.builder()
                                            .product(product)
                                            .itemNumber(item_number)
                                            .pname(pname)
                                            .itemImg(img)
                                            .build());
                            itemRepository.save(item);

                            VenderItem venderItem = venderRepository.findByVenderNumber(vender_number)
                                    .orElse(VenderItem.builder()
                                            .item(item)
                                            .fixedPrice(fixed_price)
                                            .detailInfo(detail_info)
                                            .venderNumber(vender_number)
                                            .build());
                            venderRepository.save(venderItem);

                            Delivery delivery = deliveryRepository.findByVenderItemId(venderItem.getId())
                                    .orElse(Delivery.builder()
                                            .venderItem(venderItem)
                                            .deliveryFee(0)
                                            .deliveryType(delivery_type)
                                            .build());
                            deliveryRepository.save(delivery);

                            ProductInfoByDate productInfoByDate;
                            boolean productInfoByDateExists = productInfoByDateRepository.existsByVenderItemId(venderItem.getId());
                            if(!productInfoByDateExists){
                                productInfoByDate = ProductInfoByDate.builder()
                                        .venderItem(venderItem)
                                        .dailyPrice(daily_price)
                                        .discountRate(discount_rate)
                                        .itemQuantity(item_quantity)
                                        .priceDate(currentDate)
                                        .build();
                                productInfoByDateRepository.save(productInfoByDate);
                            } else {
                                Long l = productInfoByDateRepository.countByPriceDateAndVenderItemId(currentDate, venderItem.getId());
                                if(l == 0){
                                    productInfoByDate = ProductInfoByDate.builder()
                                            .venderItem(venderItem)
                                            .dailyPrice(daily_price)
                                            .discountRate(discount_rate)
                                            .itemQuantity(item_quantity)
                                            .priceDate(currentDate)
                                            .build();
                                    productInfoByDateRepository.save(productInfoByDate);
                                }
                            }
                        }
                    }
                }
            Elements btn = d.select(".no-list-item");
            if(!btn.isEmpty()) break;
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        double durationMinutes = duration / 60000.0;
        System.out.println(durationMinutes+"분");
        return CompletableFuture.completedFuture(null);
    }
}

// 셀레니움
//        WebDriverManager.edgedriver().setup();
////        ChromeOptions options = new ChromeOptions();
//        EdgeOptions options = new EdgeOptions();
////        options.addArguments("--headless");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--window-size=1920,1080");
//        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0");
//        options.addArguments("--lang=ko_KR");

//
//        WebDriver webDriver = new EdgeDriver(options);
//        try {
//            for(int i = 10 ; i<15 ;i++){
//                int count = 0;
//                Random r = new Random();
//                int randomTimeout = r.nextInt(5) + 3;
//                webDriver.get("https://www.coupang.com/np/search?rocketAll=true&listSize=72&component=&q="+q+"&page="+ i);
//                TimeUnit.SECONDS.sleep(randomTimeout);
//                List<WebElement> productElements = webDriver.findElements(By.cssSelector(".search-product"));
//                for (WebElement productEl : productElements) {
//                    count++;
//                    String img = productEl.findElement(By.cssSelector(".search-product-wrap-img")).getAttribute("src");
//                    if (img.isEmpty()) {
//                        img = productEl.findElement(By.cssSelector(".search-product-wrap-img")).getAttribute("data-src");
//                    }
//                    String pname = productEl.findElement(By.cssSelector(".name")).getText();
//                    WebElement priceElement = productEl.findElement(By.cssSelector(".price-value"));
//                    String price = priceElement.getText();
//                    String pid = productEl.getAttribute("href");
//                    if(!pname.equals("") || pname.contains("파우치")){
//                        Product p = Product.builder().pName(pname).price(price).img(img).pID(pid).build();
//                        //                assertNull(p,p.toString());
//                        System.out.println("page "+i+ "= "+p.toString());
//                    }
//                }
//                System.out.println("count ="+count);
//                List<WebElement> btn = webDriver.findElements(By.cssSelector(".btn-next"));
//                if(btn.size() > 0){
//                    Boolean b = btn.get(0).getAttribute("class").contains("disabled");
//                    System.out.println("i :"+i +" b : "+b);
//                    if(b)
//                        break;
//                }
//            }
//
//        } finally {
//            if (webDriver != null) {
//                webDriver.quit();
//            }
//        }

//        return results;
