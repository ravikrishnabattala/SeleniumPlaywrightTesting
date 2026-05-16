package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;
import io.cucumber.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginApplications {
    private Browser browser;
    private BrowserContext browserContext;
    private Page page = null;
    private final Logger logger = LoggerFactory.getLogger(LoginApplications.class);

    public LoginApplications() {
        this.page = HooksTest.getPage();
        this.browserContext = HooksTest.getContext();
        this.browser = HooksTest.getBrowser();
        System.out.println(
                "Constructor Thread -> "
                        + Thread.currentThread().getName()
        );

        if (this.page == null) {
            throw new RuntimeException("Page is not initialized!");
        }
    }

    private void waiting(int seconds) {
        try {
            synchronized (page) {
                page.wait(seconds * 1000L);
            }
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Flipkart {string}")
    public void flipkart(String phoneNumber) {
        try {
            // Flipkart login
            page.navigate("https://www.flipkart.com/");
            page.locator("form").filter(new Locator.FilterOptions().setHasText("Enter Email/Mobile number")).getByRole(AriaRole.TEXTBOX).type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Request OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from Myntra {string}")
    public void myntra(String phoneNumber) {
        try {
            // Myntra Login
            page.navigate("https://www.myntra.com/login");
            page.locator(".mobileNumberInput").type(phoneNumber);
            page.getByRole(AriaRole.CHECKBOX).and(page.locator(".consentCheckbox")).click();
            page.getByText("CONTINUE").click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from Ajio {string}")
    public void Ajio(String phoneNumber) {
        try {
            // Ajio Login
            page.navigate("https://www.ajio.com/login");
            page.getByLabel("Enter 10 digit mobile number. It should not start with 0 or plus 91").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from Amazon {string}")
    public void Amazon(String phoneNumber) {
        try {
            // Amazon Login
            page.navigate("https://www.amazon.in");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue shopping")).click();
            page.getByText("Hello, sign in").click();
            page.getByLabel("Enter mobile number or email").type("+91" + phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CONTINUE")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from Meesho {string}")
    public void Meesho(String phoneNumber) {
        try {
            // Meesho
            page.navigate("https://www.meesho.com/auth");
            page.locator("input[type='tel']").click();
            page.locator("input[type='tel']").fill(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from Snapdeal {string}")
    public void Snapdeal(String phoneNumber) {
        try {
            // Snapdeal
            page.navigate("https://www.snapdeal.com/login");
            page.getByPlaceholder("Mobile Number/ Email").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("continue")).click();
            page.getByPlaceholder("Email", new Page.GetByPlaceholderOptions().setExact(true)).type("namaste@gmail.com");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Name")).type("Satya");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).type("Test@1269");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from Nykaa {string}")
    public void Nykaa(String phoneNumber) {
        try {
            // Nykaa
            page.navigate("https://www.nykaa.com/auth/login");
            page.getByLabel("Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Get OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from TataCLiQ {string}")
    public void TataCLiQ(String phoneNumber) {
        try {
            // TataCLiQ
            page.navigate("https://www.tatacliq.com/login");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Mobile Number")).fill(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from Pepperfry {string}")
    public void Pepperfry(String phoneNumber) {
        try {
            // Pepperfry
            page.navigate("https://www.pepperfry.com/site_login");
            page.getByText("Sign Up Now").click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("LOGIN/SIGNUP")).click();
            page.getByPlaceholder("Enter Mobile Number or Email Id").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CONTINUE")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from FirstCry {string}")
    public void FirstCry(String phoneNumber) {
        try {
            //FirstCry
            page.navigate("https://www.firstcry.com/login");
            page.getByPlaceholder("Enter your Email-Id or Mobile No.*").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CONTINUE")).click();
            page.getByPlaceholder("Full Name*").type("Satya");
            page.getByPlaceholder("Email Id*").type("satyarushi@gamil.com");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("GET OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }

    @Given("OTP from Lenskart {string}")
    public void Lenskart(String phoneNumber) {
        try {
            //Lenskart
            page.navigate("https://www.lenskart.com/customer/account/login");
            page.getByText("No thanks").click();
            page.getByLabel("User menu").click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in/Sign up")).click();
            page.getByPlaceholder("Mobile / Email").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from PolicyBazar {string}")
    public void PolicyBazar(String phoneNumber) {
        try {
            // PolicyBazar
            page.navigate("https://www.policybazaar.com/");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign in")).click();
            page.locator("#central-login-module-sign-mobile").nth(1).type(phoneNumber);
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign in with OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from ShopClues {string}")
    public void ShopClues(String phoneNumber) {
        try {
            //ShopClues
            page.navigate("https://login.shopclues.com/registration?layout=mobile");
            page.getByRole(AriaRole.TEXTBOX).click();
            page.locator("input[name='email']").type("Satya123422@gamil.com");
            page.locator("input[name='phone'][type='tel']").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register")).click();
            //Reliance Digital
            page.navigate("https://www.reliancedigital.in");
            page.getByLabel("Account Details").click();
            page.getByLabel("Enter your Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" Proceed ")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Given("OTP from Croma {string}")
    public void Croma(String phoneNumber) {
        try {
            //Croma
            page.navigate("https://www.croma.com/my-account/update-profile");
            page.getByPlaceholder("Enter your Email ID or phone number").type(phoneNumber);
            page.waitForTimeout(2000);
            Locator cont = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue"));
            BoundingBox box = cont.boundingBox();
            page.mouse().click(
                    box.x + box.width / 2,
                    box.y + box.height / 2);
            cont.click();
            cont.click();
            cont.click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from VijaySales {string}")
    public void VijaySales(String phoneNumber) {
        try {
            // VijaySales
            page.navigate("https://www.vijaysales.com/login");
            page.getByLabel("Email ID or Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from DMartReady {string}")
    public void DMartReady(String phoneNumber) {
        try {
            // DMartReady
            page.navigate("https://www.dmart.in/login");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In / Register")).click();
            page.getByRole(AriaRole.TEXTBOX).type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.locator("input[name='firstName']").type("Satya");
            page.locator("input[name='lastName']").type("Senthil");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("SAVE AND CONTINUE")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from BigBasket {string}")
    public void BigBasket(String phoneNumber) {
        try {
            // BigBasket
            page.navigate("https://www.bigbasket.com/auth/login/");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login/ Sign Up")).click();
            page.getByPlaceholder("Enter Phone number/ Email Id").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Blinkit {string}")
    public void Blinkit(String phoneNumber) {
        try {
            // Blinkit
            page.navigate("https://blinkit.com/account");
            page.getByPlaceholder("Enter mobile number").type(phoneNumber);
            page.getByText("Continue").click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Zepto {string}")
    public void Zepto(String phoneNumber) {
        try {
            // Zepto
            page.navigate("https://www.zeptonow.com");
            page.getByLabel("login").click();
            page.getByPlaceholder("Enter Phone Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from JioMart {string}")
    public void JioMart(String phoneNumber) {
        try {
            // JioMart
            page.navigate("https://www.jio.com/selfcare/login");
            page.getByLabel("Mobile Number").type(phoneNumber);
            page.getByLabel("Generate OTP").click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from NaturesBasket {string}")
    public void NaturesBasket(String phoneNumber) {
        try {
            // NaturesBasket
            page.navigate("https://www.naturesbasket.co.in/Login.aspx");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("LOGIN")).click();
            page.getByPlaceholder("+91 Enter 10 digit mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CONFIRM")).click();

            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from eBay {string}")
    public void eBay(String phoneNumber) {
        try {
            // eBay
            page.navigate("https://signin.ebay.com");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in with mobile")).click();
            page.getByTestId("phone-number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Temu {string}")
    public void Temu(String phoneNumber) {
        try {
            // Temu
            page.navigate("https://www.temu.com/login.html");
            page.getByLabel("Email or phone number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue").setExact(true)).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Shein {string}")
    public void Shein(String phoneNumber) {
        try {
            // Shein
            page.navigate("https://us.shein.com/user/auth/login");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email Address:")).fill(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue with SMS")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Westside {string}")
    public void Westside(String phoneNumber) {
        try {
            // Westside
            page.navigate("https://www.westside.com/account/login");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Accept All Cookies")).click();
            page.getByPlaceholder("Enter mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("SEND OTP")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Pantaloons {string}")
    public void Pantaloons(String phoneNumber) {
        try {
            //Pantaloons
            page.navigate("https://www.pantaloons.com/login");
            page.getByPlaceholder("Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Get OTP")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from ShoppersStop {string}")
    public void ShoppersStop(String phoneNumber) {
        try {
            //ShoppersStop
            page.navigate("https://www.shoppersstop.com/");
            page.locator("[id='radix-\\:Rlll6d6\\:'] > svg").first().click();
            page.getByPlaceholder("Enter your Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Lifestyle {string}")
    public void Lifestyle(String phoneNumber) {
        try {
            //Lifestyle
            page.navigate("https://www.lifestylestores.com/in/en/login");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("SIGN UP / SIGN IN")).click();
            page.getByPlaceholder("Enter your mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from MaxFashion {string}")
    public void MaxFashion(String phoneNumber) {
        try {
            //MaxFashion
            page.navigate("https://www.maxfashion.in/in/en/login");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("SIGN UP / SIGN IN")).click();
            page.getByPlaceholder("Enter your mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Bewakoof {string}")
    public void Bewakoof(String phoneNumber) {
        try {
            //Bewakoof
            page.navigate("https://www.bewakoof.com/login");
            page.getByPlaceholder("Enter Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CONTINUE")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from SouledStore {string}")
    public void SouledStore(String phoneNumber) {
        try {
            //SouledStore
            page.navigate("https://www.thesouledstore.com/register");
            page.getByPlaceholder("First Name *").fill("Test");
            page.getByPlaceholder("Email ID *").fill("Satyatest@gmail.com");
            page.getByPlaceholder("Choose New Password *").fill("Test@12345");
            page.getByPlaceholder("Confirm Password *").fill("Test@12345");
            page.getByPlaceholder("Please enter your birthdate *").click();
            page.getByText("1", new Page.GetByTextOptions().setExact(true)).click();
            page.getByPlaceholder("Mobile Number(For order").fill(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register")).click();
            page.getByPlaceholder("Email ID *").fill("Satyatest@gmail.com");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Register")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Clovia {string}")
    public void Clovia(String phoneNumber) {
        try {
            //Clovia
            page.navigate("https://www.clovia.com/panties/gimme-5/s/");
            page.locator("li").and(page.locator(".rftLi.profileHover")).hover();
//            page.locator("li:nth-child(14) > .fa").click();
            page.getByText("Signup").click();
            page.getByLabel("Email / Phone").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send OTP to Mobile")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Mamaearth {string}")
    public void Mamaearth(String phoneNumber) {
        try {
            // Mamaearth
            page.navigate("https://mamaearth.in");
            page.getByText("Login", new Page.GetByTextOptions().setExact(true)).click();
            page.getByPlaceholder("Mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login with OTP")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Purplle {string}")
    public void Purplle(String phoneNumber) {
        try {
            // Purplle
            page.navigate("https://www.purplle.com/profile");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login/Signup")).click();
            page.getByLabel("Enter a 10-digit mobile number").type(phoneNumber);
            page.getByText("CONTINUE").click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from SugarCosmetics {string}")
    public void SugarCosmetics(String phoneNumber) {
        try {
            // SugarCosmetics
            page.navigate("https://in.sugarcosmetics.com/account/login");
            page.frameLocator("#iframe-kp").getByPlaceholder("Phone number").click();
            page.frameLocator("#iframe-kp").getByPlaceholder("Phone number").type(phoneNumber);
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Boat {string}")
    public void Boat(String phoneNumber) {
        try {
            // Boat
            page.navigate("https://www.boat-lifestyle.com/account/login");
            page.frameLocator("#iframe-kp").getByPlaceholder("Enter Mobile Number").type(phoneNumber);
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Apple {string}")
    public void Apple(String phoneNumber) {
        try {
            // Apple
            page.navigate("https://appleid.apple.com/sign-in");
            page.getByLabel("Email or Phone Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Oppo {string}")
    public void Oppo(String phoneNumber) {
        try {
            // Oppo
            page.navigate("https://id.oppo.com/v3/auth/login");
            page.getByText("Phone").click();
            page.getByPlaceholder("Phone number").type(phoneNumber);
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Use verification code")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Puma {string}")
    public void Puma(String phoneNumber) {
        try {
            // Puma
            page.navigate("https://in.puma.com/in/en/account/login?from=account");
            page.getByPlaceholder("Phone").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Get OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Reebok {string}")
    public void Reebok(String phoneNumber) {
        try {
            // Reebok
            page.navigate("https://www.reebok.in/login");
            page.getByPlaceholder("Enter 10 digit mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("GET OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Decathlon {string}")
    public void Decathlon(String phoneNumber) {
        try {
            // Decathlon
            page.navigate("https://www.decathlon.in/login");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
            page.getByText("Phone number").click();
            page.getByLabel("Mobile phone number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Urbanic {string}")
    public void Urbanic(String phoneNumber) {
        try {
            // Urbanic
            page.navigate("https://in.urbanic.com/register");
            page.getByPlaceholder("Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Get OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from IKEA {string}")
    public void IKEA(String phoneNumber) {
        try {
            // IKEA
            page.navigate("https://www.ikea.com/in/en/profile/login");
            page.getByText("Email or Verified Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("with an OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from UrbanLadder {string}")
    public void UrbanLadder(String phoneNumber) {
        try {
            //UrbanLadder
            page.navigate("https://www.urbanladder.com/login");
            page.getByLabel("Open account menu").click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();
            page.getByPlaceholder("Enter 10-digit Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Wakefit {string}")
    public void Wakefit(String phoneNumber) {
        try {
            // Wakefit
            page.navigate("https://www.wakefit.co/login");
            page.getByPlaceholder("Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Get OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Zomato {string}")
    public void Zomato(String phoneNumber) {
        try {
            // Zomato
            page.navigate("https://www.zomato.com/login");
            page.getByPlaceholder("Phone").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send One Time Password")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Uber {string}")
    public void Uber(String phoneNumber) {
        try {
            // Uber
            page.navigate("https://auth.uber.com/login");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Enter phone number or email")).type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Ola {string}")
    public void Ola(String phoneNumber) {
        try {
            // Ola
            page.navigate("https://accounts.olacabs.com");
            page.locator("#phone-number").type(phoneNumber);
            page.getByText("Next").click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Airbnb {string}")
    public void Airbnb(String phoneNumber) {
        try {
            // Airbnb
            page.navigate("https://www.airbnb.com/login");
            page.getByLabel("Phone number or email").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from OYO {string}")
    public void OYO(String phoneNumber) {
        try {
            // OYO
            page.navigate("https://www.oyorooms.com/login");
            page.getByLabel("Enter Phone Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify Number")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from MakeMyTrip {string}")
    public void MakeMyTrip(String phoneNumber) {
        try {
            // MakeMyTrip
            page.navigate("https://www.makemytrip.com/login");
            page.getByPlaceholder("Enter Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Goibibo {string}")
    public void Goibibo(String phoneNumber) {
        try {
            // Goibibo
            page.navigate("https://www.goibibo.com/login");
            page.locator("[type='text'][name='phone']").type(phoneNumber);
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Yatra {string}")
    public void Yatra(String phoneNumber) {
        try {
            // Yatra
            page.navigate("https://www.yatra.com");
            page.getByText("Login / Signup").click();
            page.getByLabel("Email Id / Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from leartrip {string}")
    public void leartrip(String phoneNumber) {
        try {
            // leartrip
            page.navigate("https://www.cleartrip.com");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log in")).click();
            page.getByPlaceholder("Enter mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Get OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from BookMyShow {string}")
    public void BookMyShow(String phoneNumber) {
        try {
            // BookMyShow
            page.navigate("https://in.bookmyshow.com/explore/login");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in")).click();
            page.getByPlaceholder("Continue with mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from RedBus {string}")
    public void RedBus(String phoneNumber) {
        try {
            // RedBus
            page.navigate("https://www.redbus.in/login");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Account")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log in")).click();
            page.getByLabel("Mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.getByText("I'm not a robot").click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Dominos {string}")
    public void Dominos(String phoneNumber) {
        try {
            // Dominos
            page.navigate("https://pizzaonline.dominos.co.in/login");
            page.getByTestId("user-input").type(phoneNumber);
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from McDonalds {string}")
    public void McDonalds(String phoneNumber) {
        try {
            // McDonalds
            page.navigate("https://mcdelivery.co.in/auth-with-otp");
            page.getByPlaceholder("10 Digit Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify Mobile")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from UrbanCompany {string}")
    public void UrbanCompany(String phoneNumber) {
        try {
            // UrbanCompany
            page.navigate("https://www.urbancompany.com/login");
            page.locator(".css-175oi2r").click();
            page.getByText("Login").click();
            page.getByPlaceholder("Enter your phone number").type(phoneNumber);
            page.getByText("Continue").click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from NoBroker {string}")
    public void NoBroker(String phoneNumber) {
        try {
            // NoBroker
            page.navigate("https://www.nobroker.in/login");
            page.getByText("Sign up").click();
            page.getByPlaceholder("Enter Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from 99acres {string}")
    public void acres(String phoneNumber) {
        try {
            // 99acres
            page.navigate("https://www.99acres.com/login");
            page.getByText("LOGIN / REGISTER").click();
            page.locator("[title='Phone Number']").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from OLX {string}")
    public void OLX(String phoneNumber) {
        try {
            // OLX
            page.navigate("https://www.olx.in/account");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue with Phone")).click();
            page.getByPlaceholder("Phone Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Quikr {string}")
    public void Quikr(String phoneNumber) {
        try {
            // Quikr
            page.navigate("https://www.quikr.com/login");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Login/Register")).click();
            page.getByLabel("Enter Mobile Number / Email").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Telegram {string}")
    public void Telegram(String phoneNumber) {
        try {
            // Telegram
            page.navigate("https://web.telegram.org");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log in by phone number")).click();
            page.getByLabel("Your phone number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Twitter {string}")
    public void Twitter(String phoneNumber) {
        try {
            // Twitter/X
            page.navigate("https://x.com/i/flow/login");
            page.getByText("Phone, email, or username").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Reddit {string}")
    public void Reddit(String phoneNumber) {
        try {
            // Reddit
            page.navigate("https://www.reddit.com/login");
            page.getByText("Continue with Phone Number").click();
            page.getByLabel("Phone Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Canva {string}")
    public void Canva(String phoneNumber) {
        try {
            // Canva
            page.navigate("https://www.canva.com/login");
            page.getByText("Continue with phone number").click();
            page.getByPlaceholder("Phone number or email").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Spotify {string}")
    public void Spotify(String phoneNumber) {
        try {
            // Spotify
            page.navigate("https://accounts.spotify.com/en/v2/login/phone");
            page.locator("input[type='tel'][name='phoneNumber']").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Netflix {string}")
    public void Netflix(String phoneNumber) {
        try {
            // Netflix
            page.navigate("https://www.netflix.com/login");
            page.getByLabel("Email or mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Hotstar {string}")
    public void Hotstar(String phoneNumber) {
        try {
            // Hotstar
            page.navigate("https://www.hotstar.com/in/login");
            page.getByLabel("Enter mobile number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Get OTP")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from SonyLIV {string}")
    public void SonyLIV(String phoneNumber) {
        try {
            // SonyLIV
            page.navigate("https://www.sonyliv.com/signin");
            page.getByLabel("Enter your Mobile Number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Zee5 {string}")
    public void Zee5(String phoneNumber) {
        try {
            // Zee5
            page.navigate("https://www.zee5.com/register");
            page.getByPlaceholder("Enter email or mobile number").type(phoneNumber);
            page.getByRole(AriaRole.CHECKBOX).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create account")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("OTP from Zoom {string}")
    public void Zoom(String phoneNumber) {
        try {
            // Zoom
            page.navigate("https://zoom.us/signin");
            page.getByLabel("Email or phone number").type(phoneNumber);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next")).click();
            page.waitForTimeout(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        page.waitForTimeout(5000);
    }

}
