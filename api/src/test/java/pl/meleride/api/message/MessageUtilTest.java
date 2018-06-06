package pl.meleride.api.message;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class MessageUtilTest {

  @Test
  public void coloredSingleTest() {
    String expected = "§7Example §cstring§r.";
    String actual = MessageUtil.colored("&7Example &cstring&r.");

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void coloredListTest() {
    List<String> expected = Arrays.asList("§7Hello!", "§cHow are you?", "§6I'm fine, thanks!");
    List<String> actual = MessageUtil.colored(
        Arrays.asList("&7Hello!", null, "&cHow are you?", null, "&6I'm fine, thanks!"));

    Assert.assertEquals(expected, actual);
  }

}
