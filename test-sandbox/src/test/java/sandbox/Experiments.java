package sandbox;

import utils.PackageExecutor;


public class Experiments {

    private static PackageExecutor executor = new PackageExecutor();

    public static void main(String... args) {
        executor.execute("documents");
    }

}
