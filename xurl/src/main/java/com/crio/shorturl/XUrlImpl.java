package com.crio.shorturl;

import java.util.HashMap;
import java.util.UUID;

public class XUrlImpl implements XUrl {
    private HashMap<String, String>longToShort = new HashMap<>();
    private HashMap<String, String>shortToLong = new HashMap<>();
    private HashMap<String, Integer>longUrlHits = new HashMap<>();

    private String usingRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public String registerNewUrl(String longUrl) {
        String UUID = usingRandomUUID();
        if(longToShort.get(longUrl) != null) {
            return longToShort.get(longUrl);
        }
        StringBuilder s = new StringBuilder("http://short.url/");
        StringBuilder shortUrl = new StringBuilder(UUID.substring(0, 9));
        shortUrl = s.append(shortUrl);
        String sU = shortUrl.toString();
        longToShort.put(longUrl, sU);
        shortToLong.put(sU, longUrl);
        return sU;
    }
    
    public String registerNewUrl(String longUrl, String shortUrl) {
        if(shortToLong.containsKey(shortUrl)) {
            return null;
        }
        longToShort.put(longUrl, shortUrl);
        shortToLong.put(shortUrl, longUrl);
        return shortUrl;
    }

    public Integer getHitCount(String longUrl) {
        if(longUrlHits.containsKey(longUrl)) {
            return longUrlHits.get(longUrl);
        }
        return 0;
    }

    public String getUrl(String shortUrl) {
        if(shortToLong.containsKey(shortUrl)) {
            if(longUrlHits.containsKey(shortToLong.get(shortUrl))) {
                longUrlHits.put(shortToLong.get(shortUrl), longUrlHits.get(shortToLong.get(shortUrl)) + 1);
            } else {
                longUrlHits.put(shortToLong.get(shortUrl), 1);
            }
        }
        return shortToLong.get(shortUrl);
    }

    public String delete(String longUrl) {
        String sU = longToShort.get(longUrl);
        longToShort.remove(longUrl);
        shortToLong.remove(sU);
        return sU;
    }


}