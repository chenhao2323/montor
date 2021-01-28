//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package luncene;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tomcat.util.http.fileupload.IOUtils;

public class IndexFiles {
    private IndexFiles() {
    }

    public static void main(String[] args) {
        String usage = "java org.apache.lucene.demo.IndexFiles [-index INDEX_PATH] [-docs DOCS_PATH] [-update]\n\nThis indexes the documents in DOCS_PATH, creating a Lucene indexin INDEX_PATH that can be searched with SearchFiles";
        String indexPath = "index";
        String docsPath = null;
        boolean create = true;

        for(int i = 0; i < args.length; ++i) {
            if ("-index".equals(args[i])) {
                indexPath = args[i + 1];
                ++i;
            } else if ("-docs".equals(args[i])) {
                docsPath = args[i + 1];
                ++i;
            } else if ("-update".equals(args[i])) {
                create = false;
            }
        }

        if (docsPath == null) {
            System.err.println("Usage: " + usage);
            System.exit(1);
        }

        Path docDir = Paths.get(docsPath);
        if (!Files.isReadable(docDir)) {
            System.out.println("Document directory '" + docDir.toAbsolutePath() + "' does not exist or is not readable, please check the path");
            System.exit(1);
        }

        Date start = new Date();

        try {
            System.out.println("Indexing to directory '" + indexPath + "'...");
            Directory dir = FSDirectory.open(Paths.get(indexPath));
            Map<String, Analyzer> map = new HashMap<>();
            map.put("content",new SmartChineseAnalyzer());
            Analyzer analyzer = new PerFieldAnalyzerWrapper(new StandardAnalyzer(),map);
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

            if (create) {
                iwc.setOpenMode(OpenMode.CREATE);
            } else {
                iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
            }
            IndexWriter writer = new IndexWriter(dir, iwc);
            indexDocs(writer, docDir);
            System.out.println(iwc.getUseCompoundFile());
           // writer.forceMerge(1);
            writer.close();
            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");
        } catch (IOException var12) {
            System.out.println(" caught a " + var12.getClass() + "\n with message: " + var12.getMessage());
        }

    }

    static void indexDocs(IndexWriter writer, Path path) throws IOException {
        if (Files.isDirectory(path, new LinkOption[0])) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try {
                        IndexFiles.indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
                    } catch (IOException var4) {
                        ;
                    }

                    return FileVisitResult.CONTINUE;
                }
        });
        } else {
            indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
        }

    }

    static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
        InputStream stream = Files.newInputStream(file);
        Throwable var5 = null;

        try {
            PerFieldAnalyzerWrapper w;
            Document doc = new Document();
            Field pathField = new StringField("path", file.toString(), Store.YES);
            doc.add(pathField);

            doc.add(new StringField("modified",String.valueOf(lastModified),Store.YES));
            doc.add(new NumericDocValuesField("modified",lastModified));
            doc.add(new LongPoint("modified", new long[]{lastModified}));
            Reader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            char[] buffer = new char[10240];
            reader.read(buffer);

            FieldType type = new FieldType();
            type.setStored(true);
            type.setTokenized(true);
            type.setIndexOptions(IndexOptions.DOCS);
            System.out.println(String.valueOf(lastModified));
            doc.add(new Field("content",String.valueOf(buffer),type));
            if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                System.out.println("adding " + file);
                writer.addDocument(doc);
            } else {
                System.out.println("updating " + file);
                writer.updateDocument(new Term("path", file.toString()), doc);
            }
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            if (stream != null) {
                if (var5 != null) {
                    try {
                        stream.close();
                    } catch (Throwable var14) {
                        var5.addSuppressed(var14);
                    }
                } else {
                    stream.close();
                }
            }

        }

    }
}
