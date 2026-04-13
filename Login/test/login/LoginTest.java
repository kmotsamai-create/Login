package login;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit 4 test class for the Login validation methods.
 * Tests every required pass/fail case.
 */
public class LoginTest {

    // ========== TESTS FOR checkUserName() ==========

    @Test
    public void testUserName_Valid_ShouldPass() {
        // Valid username: contains underscore, length 5 or less
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "+27831234567");
        assertTrue("Username 'kyl_1' should be valid", user.checkUserName());
    }

    @Test
    public void testUserName_NoUnderscore_ShouldFail() {
        // Invalid: missing underscore
        Login user = new Login("John", "Doe", "kyle1", "Pass123!", "+27831234567");
        assertFalse("Username without underscore should fail", user.checkUserName());
    }

    @Test
    public void testUserName_TooLong_ShouldFail() {
        // Invalid: length > 5 (even with underscore)
        Login user = new Login("John", "Doe", "kyle_123", "Pass123!", "+27831234567");
        assertFalse("Username longer than 5 chars should fail", user.checkUserName());
    }

    // ========== TESTS FOR checkPasswordComplexity() ==========

    @Test
    public void testPassword_Strong_ShouldPass() {
        // Meets all rules: 8+ chars, uppercase, digit, special char
        Login user = new Login("John", "Doe", "kyl_1", "Ch&sec@ke99!", "+27831234567");
        assertTrue("Strong password should pass", user.checkPasswordComplexity());
    }

    @Test
    public void testPassword_NoUppercase_ShouldFail() {
        Login user = new Login("John", "Doe", "kyl_1", "pass123!", "+27831234567");
        assertFalse("Password missing uppercase should fail", user.checkPasswordComplexity());
    }

    @Test
    public void testPassword_NoDigit_ShouldFail() {
        Login user = new Login("John", "Doe", "kyl_1", "Password!", "+27831234567");
        assertFalse("Password missing digit should fail", user.checkPasswordComplexity());
    }

    @Test
    public void testPassword_NoSpecialChar_ShouldFail() {
        Login user = new Login("John", "Doe", "kyl_1", "Password123", "+27831234567");
        assertFalse("Password missing special character should fail", user.checkPasswordComplexity());
    }

    @Test
    public void testPassword_TooShort_ShouldFail() {
        Login user = new Login("John", "Doe", "kyl_1", "Pa1!", "+27831234567");
        assertFalse("Password shorter than 8 chars should fail", user.checkPasswordComplexity());
    }

    // ========== TESTS FOR checkCellPhoneNumber() ==========

    @Test
    public void testCellPhone_ValidSA_ShouldPass() {
        // Exactly +27 followed by 9 digits
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "+27831234567");
        assertTrue("Valid SA cell number should pass", user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhone_MissingPlus_ShouldFail() {
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "27831234567");
        assertFalse("Missing '+' should fail", user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhone_WrongCode_ShouldFail() {
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "+2783123456"); // only 8 digits after +27
        assertFalse("Incorrect length should fail", user.checkCellPhoneNumber());
    }

    // ========== TESTS FOR registerUser() ==========

    @Test
    public void testRegisterUser_AllValid_ReturnsSuccess() {
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "+27831234567");
        assertEquals("Registration successful.", user.registerUser());
    }

    @Test
    public void testRegisterUser_InvalidUsername_ReturnsSpecificError() {
        Login user = new Login("John", "Doe", "kyle", "Pass123!", "+27831234567");
        String expected = "Username is not correctly formatted; please ensure that your " +
                          "username contains an underscore and is no more than five characters in length.";
        assertEquals(expected, user.registerUser());
    }

    @Test
    public void testRegisterUser_InvalidPassword_ReturnsSpecificError() {
        Login user = new Login("John", "Doe", "kyl_1", "weak", "+27831234567");
        String expected = "Password is not correctly formatted; please ensure that the password contains " +
                          "at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(expected, user.registerUser());
    }

    @Test
    public void testRegisterUser_InvalidCell_ReturnsSpecificError() {
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "0831234567");
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.",
                     user.registerUser());
    }

    // ========== TESTS FOR loginUser() ==========

    @Test
    public void testLoginUser_CorrectCredentials_ReturnsTrue() {
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "+27831234567");
        assertTrue("Correct username and password should return true",
                   user.loginUser("kyl_1", "Pass123!"));
    }

    @Test
    public void testLoginUser_WrongUsername_ReturnsFalse() {
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "+27831234567");
        assertFalse("Wrong username should return false",
                    user.loginUser("wrong", "Pass123!"));
    }

    @Test
    public void testLoginUser_WrongPassword_ReturnsFalse() {
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "+27831234567");
        assertFalse("Wrong password should return false",
                    user.loginUser("kyl_1", "wrongPass"));
    }

    // ========== TESTS FOR returnLoginStatus() ==========

    @Test
    public void testReturnLoginStatus_Success_ReturnsWelcomeMessage() {
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "+27831234567");
        String expected = "Welcome John, Doe it is great to see you again.";
        assertEquals(expected, user.returnLoginStatus("kyl_1", "Pass123!"));
    }

    @Test
    public void testReturnLoginStatus_Failure_ReturnsErrorMessage() {
        Login user = new Login("John", "Doe", "kyl_1", "Pass123!", "+27831234567");
        assertEquals("Username or password incorrect, please try again.",
                     user.returnLoginStatus("kyl_1", "wrong"));
    }
}