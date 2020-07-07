import com.google.common.collect.Lists;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestYamlList {
    public static void main(String[] args) {
        DumperOptions opts = new DumperOptions();
        opts.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(opts);

        String s = yaml.dump(new ArrayList<>(Stream.generate(UUID::randomUUID).limit(100).collect(Collectors.toList())));

        System.out.println(s);
    }
}
