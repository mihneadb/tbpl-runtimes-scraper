# tbpl-testtimes-scraper

Output JSON data of build and test time from a revision's TBPL HTML page.

Because of the way TBPL injects all the data via JS you need to provide your
own (local) HTML file (i.e. go to the revision that you want, wait for the
data to load and then Save Page As). I haven't found a way to have the data
load at "scrape time". Please send me your suggestions if you have any.

You need [lein](http://leiningen.org/). Or use the static jar in the tree.

## Usage

`lein run /path/to/tbpl.html`

or

`java -jar tbpl-testtimes.jar /path/to/tbpl.html`

