Feature: Run testcases of playwright

  Background: background

  Scenario: Login into Instagram
    Given Login to Instagram userId = 'userId' and password = 'password' playwright


  Scenario Outline: chatting.....
    Given Login to Instagram playwright
    Then Send message '<message>' to user '<userId>' on instagram playwright

    Examples:
      | testCaseId | message         | userId     |
      | 1234567    | Hii Googles.... | Zoozubieee |

  Scenario Outline: Login into Linkedin
    Given Login to Linkedin username='<username>' & password='<passcode>'
    Examples:
      | username                      | passcode |
      | ravikrishnabattala5@gmail.com | #####    |


  Scenario: Apply for Jobs
    Given Apply recommended jobs


  Scenario Outline: Login to Naukri
    Given Login to Naukri userId = '<username>' and password = '<passcode>'

    Examples:
      | username                      | passcode |
      | ravikrishnabattala5@gmail.com | #####    |


  Scenario: Apply Jobs
    Given Apply naukri jobs

  Scenario: Share Interest
    Given Share Interest to Job


  Scenario Outline: Login Apps
    Given OTP from Flipkart '<phoneNumber>'
#    And OTP from Myntra '<phoneNumber>'
#    And OTP from Ajio '<phoneNumber>'
#    And OTP from Amazon '<phoneNumber>'
#    And OTP from Meesho '<phoneNumber>'
#    And OTP from Snapdeal '<phoneNumber>'
#    And OTP from Nykaa '<phoneNumber>'
#    And OTP from TataCLiQ '<phoneNumber>'
#    And OTP from Pepperfry '<phoneNumber>'
#    And OTP from FirstCry '<phoneNumber>'
#    And OTP from Lenskart '<phoneNumber>'
#    And OTP from PolicyBazar '<phoneNumber>'
#    And OTP from ShopClues '<phoneNumber>'
#    And OTP from Croma '<phoneNumber>'
#    And OTP from VijaySales '<phoneNumber>'
#    And OTP from DMartReady '<phoneNumber>'
#    And OTP from BigBasket '<phoneNumber>'
#    And OTP from Blinkit '<phoneNumber>'
#    And OTP from Zepto '<phoneNumber>'
#    And OTP from JioMart '<phoneNumber>'
#    And OTP from NaturesBasket '<phoneNumber>'
#    And OTP from eBay '<phoneNumber>'
#    And OTP from Temu '<phoneNumber>'
#    And OTP from Shein '<phoneNumber>'
#    And OTP from Westside '<phoneNumber>'
#    And OTP from Pantaloons '<phoneNumber>'
#    And OTP from ShoppersStop '<phoneNumber>'
#    And OTP from Lifestyle '<phoneNumber>'
#    And OTP from MaxFashion '<phoneNumber>'
#    And OTP from Bewakoof '<phoneNumber>'
#    And OTP from SouledStore '<phoneNumber>'
#    And OTP from Clovia '<phoneNumber>'
#    And OTP from Mamaearth '<phoneNumber>'
#    And OTP from Purplle '<phoneNumber>'
#    And OTP from SugarCosmetics '<phoneNumber>'
#    And OTP from Boat '<phoneNumber>'
#    And OTP from Apple '<phoneNumber>'
#    And OTP from Oppo '<phoneNumber>'
#    And OTP from Puma '<phoneNumber>'
#    And OTP from Reebok '<phoneNumber>'
#    And OTP from Decathlon '<phoneNumber>'
#    And OTP from Urbanic '<phoneNumber>'
#    And OTP from IKEA '<phoneNumber>'
#    And OTP from UrbanLadder '<phoneNumber>'
#    And OTP from Wakefit '<phoneNumber>'
#    And OTP from Zomato '<phoneNumber>'
#    And OTP from Uber '<phoneNumber>'
#    And OTP from Ola '<phoneNumber>'
#    And OTP from Airbnb '<phoneNumber>'
#    And OTP from OYO '<phoneNumber>'
#    And OTP from MakeMyTrip '<phoneNumber>'
#    And OTP from Goibibo '<phoneNumber>'
#    And OTP from Yatra '<phoneNumber>'
#    And OTP from leartrip '<phoneNumber>'
#    And OTP from BookMyShow '<phoneNumber>'
#    And OTP from RedBus '<phoneNumber>'
#    And OTP from Dominos '<phoneNumber>'
#    And OTP from McDonalds '<phoneNumber>'
#    And OTP from UrbanCompany '<phoneNumber>'
#    And OTP from NoBroker '<phoneNumber>'
#    And OTP from 99acres '<phoneNumber>'
#    And OTP from OLX '<phoneNumber>'
#    And OTP from Quikr '<phoneNumber>'
#    And OTP from Telegram '<phoneNumber>'
#    And OTP from Twitter '<phoneNumber>'
#    And OTP from Reddit '<phoneNumber>'
#    And OTP from Canva '<phoneNumber>'
#    And OTP from Spotify '<phoneNumber>'
#    And OTP from Netflix '<phoneNumber>'
#    And OTP from Hotstar '<phoneNumber>'
#    And OTP from SonyLIV '<phoneNumber>'
#    And OTP from Zee5 '<phoneNumber>'
#    And OTP from Zoom '<phoneNumber>'

    @GenerateOTPs
    Examples:
      | phoneNumber |
#      | #########  |
