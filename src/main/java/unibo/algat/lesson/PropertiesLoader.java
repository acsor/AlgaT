package unibo.algat.lesson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

abstract class PropertiesLoader {
	/**
	 * <p>Returns a list of entries from the project directory {@code dir}.
	 * Due to the nature of the Java language/VM, loading
	 * content from jar files differs from performing the same operation
	 * through IDEs, for example. The complexities that ensue are encapsulated
	 * and handled by this method.</p>
	 * <p><b>Note:</b> the use of this method was motivated by
	 * having to list folder entries from within a jar file; relevant infos
	 * might be found in the link below.</p>
	 *
	 * @param dir Path string identifying the directory to query resources for
	 * @return An iterator of {@link Path}s.
	 * @see <a href="https://stackoverflow.com/questions/1429172/">This SO
	 * question</a>.
	 */
	Iterator<Path> listProjectDir(String dir) {
		URI dirUri;
		Path dirPath;
		FileSystem fs = null;
		Stream<Path> paths = null;

		try {
			dirUri = getClass().getResource(dir).toURI();

			if (dirUri.getScheme().equals("jar")) {
				fs = FileSystems.newFileSystem(dirUri, new HashMap<>());
				dirPath = fs.getPath(dir);
			} else {
				dirPath = Paths.get(dirUri);
			}

			paths = Files.list(dirPath);

			if (fs != null)
				fs.close();
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();

			return Collections.emptyIterator();
		}

		return paths.iterator();
	}
}
