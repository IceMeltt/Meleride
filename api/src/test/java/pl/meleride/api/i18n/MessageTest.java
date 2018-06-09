package pl.meleride.api.i18n;

import java.io.InputStreamReader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageTest {

  @Mock
  private JavaPlugin plugin;

  @Before
  public void setup() {
    YamlConfiguration yaml = YamlConfiguration.loadConfiguration(
        new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("messages.yml")));
    FileConfiguration fileConfiguration = Mockito.mock(FileConfiguration.class);

    Mockito.when(fileConfiguration.getString(Matchers.anyString()))
        .then(answer -> yaml.getString((String) answer.getArguments()[0]));
    Mockito.when(plugin.getConfig()).thenReturn(fileConfiguration);
  }

  @Test
  public void builderTest() {
    String expected = "Hello world!";
    String actual = new MessageBuilder(plugin, "test-field")
        .withField("VARIABLE", "world").toString();

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void builderViaBundlerTest() {
    String expected = "Hello world!";
    String actual = MessageBundler.create(plugin, "test-field")
        .withField("VARIABLE", "world").toString();

    Assert.assertEquals(expected, actual);
  }

}
