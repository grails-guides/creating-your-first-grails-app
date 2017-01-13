@Grapes(
        @Grab(group='org.yaml', module='snakeyaml', version='1.4')
)
import groovy.io.FileType
import org.yaml.snakeyaml.Yaml

Properties gradleProperties = new Properties()
File propertiesFile = new File('gradle.properties')
propertiesFile.withInputStream {
    gradleProperties.load(it)
}

def rootRespositoryName = 'grails-guides'
def buildFolder = 'build'
def bookOutputFolder = "${buildFolder}/book"
def bookOutputImagesFolder = "${bookOutputFolder}/images"
def bookOutputSourceFolder = "${bookOutputFolder}/src"

if ( new File(bookOutputFolder).exists() ) {
    new File(bookOutputFolder).deleteDir()
}

[buildFolder, bookOutputFolder, bookOutputImagesFolder, bookOutputSourceFolder].each { String folderName ->
    def folder = new File(folderName)
    if( !folder.exists() ) {
        folder.mkdirs()
    }
}
def frontCoverImageName = 'front-cover.png'

def sout = new StringBuilder()
serr = new StringBuilder()
Process process = "cp ${frontCoverImageName} ${bookOutputImagesFolder}/${frontCoverImageName}".execute()
process.consumeProcessOutput(sout, serr)
process.waitForOrKill(1000)

new AntBuilder().copy(todir: bookOutputSourceFolder) {
    fileset(dir: 'complete', includes: "**")
}

new AntBuilder().copy(todir: bookOutputFolder) {
    fileset(dir: 'build/resources/grails-guides-master/src/main/docs', includes: "**")
}

new AntBuilder().copy(todir: bookOutputFolder) {
    fileset(dir: 'src/main/docs/guide', includes: "**")
}

def adocList = []

def dir = new File(bookOutputFolder)
dir.eachFileRecurse (FileType.FILES) { file ->
    adocList << file
}

adocList.each {
    if ( it.path.endsWith('.adoc') ) {
        new AntBuilder().replace(file: it.path, token: 'include::{commondir}/', value: 'include::')
    }
}


def bookName = "${gradleProperties.githubSlug.replaceAll("${rootRespositoryName}/" as String, '')}.adoc"


def bookFile = new File("${bookOutputFolder}/${bookName}")
bookFile.createNewFile()
bookFile.text = ''
bookFile << "= ${gradleProperties.title}\n"
bookFile << "${gradleProperties.authors}\n"
bookFile << 'v1.0, 2017-01-13\n'
bookFile << ':doctype: book\n'
bookFile << ":producer: ${gradleProperties.bookProducer}\n"
bookFile << ":keywords: ${gradleProperties.bookKeywords}\n"
bookFile << ":copyright: ${gradleProperties.copyright}\n"
bookFile << ":imagesdir: images\n"
bookFile << ":sourcedir: src\n"
bookFile << ":front-cover-image: image:front-cover.png[Front Cover,1050,1600]\n"
bookFile << ":toc:\n"

Yaml yaml = new Yaml()
def data = new File('src/main/docs/guide/toc.yml').text
def map = (Map) yaml.load(data)

for ( String chapterName : map.keySet() ) {
    bookFile << "include::${chapterName}.adoc[]\n"
    def chapterFile = new File("${bookOutputFolder}/${chapterName}.adoc")
    def lines = chapterFile.readLines()
    lines = lines.plus(0, "[[${chapterName}.adoc]]\n= ${map[chapterName].title}\n")
    chapterFile.text = lines.join('\n')

    for ( String subchapterName : map[chapterName].keySet().findAll { it != 'title' } ) {
        chapterFile.append("\n\ninclude::${subchapterName}.adoc[]")
        def subchapterFile = new File("${bookOutputFolder}/${subchapterName}.adoc")
        lines = subchapterFile.readLines()
        lines = lines.plus(0, "== ${map[chapterName][subchapterName]}\n")
        subchapterFile.text = lines.join('\n')
    }

}