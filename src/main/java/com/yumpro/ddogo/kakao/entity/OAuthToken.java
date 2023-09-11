package com.yumpro.ddogo.kakao.entity;

import lombok.Data;

 /*
   access_token": "q2z6ThdwjMzxpYixlAZYpLUjK9dql2wE4q9v0PgKCj1ymAAAAYptgsNo",
  "token_type": "bearer",
  "refresh_token": "E7FTyAf4DAUKmQFFa2H4sKzMzVEKaw1iWPWhal6pCj1ymAAAAYptgsNo",
  "id_token": "eyJraWQiOiI5ZjI1MmRhZGQ1ZjIzM2Y5M2QyZmE1MjhkMTJmZWEiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI1YTM3ODU1NWQxYjliODE3MTNhZjk2MDljZTA3MWM5ZCIsInN1YiI6IjMwMDYwNTIzOTQiLCJhdXRoX3RpbWUiOjE2OTQwNTQ0MDAsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwibmlja25hbWUiOiLsnbTrpZjslYgiLCJleHAiOjE2OTQwNjE2MDAsImlhdCI6MTY5NDA1NDQwMCwiZW1haWwiOiJkdXdraTE1OUBuYXZlci5jb20ifQ.X0JBoElpgygAjndUuz5SOepHW3bruQ54v2g7EiL1Jbg-2b3UZl94ES40ddZ-xbeDFL0fjXMDBSkujZ_cdxR2v2IJjNIlkrcO5fBbzIiXXH6_Kp6Sud5afoK5T3DMCDkZYMnOKx0mjbOUxGi7ds3wK8lGaJLUsFuFzRuo7AlXijRlJPjA_gCX0VMO7l_3yp-ZHxrnV89UU0GQAlgydUQzvm63_04wQyZD9a1gFQKtcYz4pvf_J1eGHmSowMd8SLAyJzZy5Vk5JYsJE6ml0tAYsghuaKF1HBAChN-Tl055nbKkkbfbrN9O4HUU6uR-LZ_TrzfM2MwXyZWOhaBR1Zn9RA",
  "expires_in": 7199,
  "scope": "age_range birthday account_email gender openid profile_nickname",
  "refresh_token_expires_in": 5183999
    *
    * */

@Data
public class OAuthToken {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private String id_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;

}
