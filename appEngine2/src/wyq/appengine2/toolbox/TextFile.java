package wyq.appengine2.toolbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

/**
 * <p>
 * This class extends the java.io.File and embeds the read/write methods through
 * which you can read or write all the content of a text file at once or once a
 * line.
 * </p>
 * <p>
 * Example:
 * 
 * <pre>
 * // Read all at once, no need to close the file.
 * String allTxtContent = new TextFile("someFile.txt").readAll();
 * 
 * // Read line by line, need close. 
 * String line = null;
 * TextFile txtFile = new TextFile("file.txt");
 * while((line = txtFile.readLine()) != null) {
 * 	// process line
 * 	...
 * }
 * txtFile.close();
 * 
 * // Appending write all, no need to close.
 * TextFile txtFile = new TextFile("write_to.txt");
 * txtFile.writeAll(allTxtContent, true); // Set false to overwrite the file.
 * 
 * // Write line by line, need close. 
 * TextFile txtFile = new TextFile("write_to_line.txt");
 * // In line by line writing mode, appending is true by default.
 * // And there is no way to change that.
 * txtFile.writeLine("one line");
 * txtFile.close();
 * 
 * </pre>
 * 
 * </p>
 * 
 * @author dewafer
 * @version 2.1
 */
public class TextFile extends File implements Closeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -241106835320530104L;

	public static final String LINE_SEP = System.getProperty("line.separator");

	public TextFile(File parent, String child) {
		super(parent, child);
	}

	public TextFile(String parent, String child) {
		super(parent, child);
	}

	public TextFile(String pathname) {
		super(pathname);
	}

	public TextFile(URI uri) {
		super(uri);
	}

	private TextFileReaderWriter readerWriter = new TextFileReaderWriter();

	public String readAll() throws IOException {
		StringBuilder sb = new StringBuilder();
		try {
			readerWriter.openRead();
			String line = null;
			while ((line = readerWriter.readLine()) != null) {
				sb.append(line);
				sb.append(LINE_SEP);
			}
		} finally {
			readerWriter.closeRead();
		}
		return sb.toString();
	}

	public void writeAll(String content, boolean append) throws IOException {
		try {
			readerWriter.openWrite(append);
			readerWriter.write(content);
			readerWriter.flush();
		} finally {
			readerWriter.closeWrite();
		}
	}

	public String readLine() throws IOException {
		String line = null;
		readerWriter.openRead();
		line = readerWriter.readLine();
		return line;
	}

	public void writeLine(String line) throws IOException {
		readerWriter.openWrite(true);
		readerWriter.writeLine(line);
	}

	public void close() throws IOException {
		readerWriter.close();
	}

	public void reset() throws IOException {
		readerWriter.reset();
	}

	public long skip(long arg0) throws IOException {
		return readerWriter.skip(arg0);
	}

	public void flush() throws IOException {
		readerWriter.flush();
	}

	class TextFileReaderWriter {

		private BufferedReader reader;
		private BufferedWriter writer;

		public void openRead() throws FileNotFoundException {
			if (reader == null) {
				reader = new BufferedReader(new FileReader(TextFile.this));
			}
		}

		public void openWrite(boolean append) throws IOException {
			if (writer == null) {
				if (!TextFile.this.exists()) {
					TextFile.this.getParentFile().mkdirs();
					TextFile.this.createNewFile();
				}
				writer = new BufferedWriter(new FileWriter(TextFile.this,
						append));

			}
		}

		public void close() throws IOException {
			closeRead();
			closeWrite();
		}

		public void closeRead() throws IOException {
			if (reader != null) {
				reader.close();
				reader = null;
			}
		}

		public void closeWrite() throws IOException {
			if (writer != null) {
				writer.close();
				writer = null;
			}
		}

		public String readLine() throws IOException {
			String line = null;
			if (reader != null) {
				line = reader.readLine();
			}
			return line;
		}

		public void writeLine(String line) throws IOException {
			if (writer != null) {
				writer.write(line);
				writer.newLine();
			}
		}

		public void reset() throws IOException {
			if (reader != null) {
				reader.reset();
			}
		}

		public long skip(long arg0) throws IOException {
			long skipped = 0;
			if (reader != null) {
				skipped = reader.skip(arg0);
			}
			return skipped;
		}

		public void flush() throws IOException {
			if (writer != null) {
				writer.flush();
			}
		}

		public void write(String str) throws IOException {
			if (writer != null) {
				writer.write(str);
			}
		}

	}

}
