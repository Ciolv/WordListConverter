# WordListConverter

WordListConverter (or `wlc`) is used to convert a word (or sentence) list with matching pairs of a given format to
another one.  
It was created specifically to just copy words from the [babbel.com](https://babbel.com) repetition menu and reformat
them, so that the list can be imported to the [ForgetMeNot](https://github.com/tema6120/ForgetMeNot) app.

**Before using the application**, read the list of known bugs below.

## Features

- Convert [babbel.com](https://babbel.com) repetition words to [ForgetMeNot](https://github.com/tema6120/ForgetMeNot)
  Q&A format.
- Deduplicate lists automatically.
- Manual deduplication by using the `--qna` flag and pointing source to a Q&A formatted file.

## Usage

```shell
$ java -jar ./wlc.jar --help
Usage: wlc [<options>] <source> <destination>

  This program is used to extract word pairs from a source, format those pairs and write the formatted pairs to a
  destination.

Options:
  -v, --verbose  Enable verbose mode
  --babbel       Set the Babbel data format as input format. [Default]
  --qna          Set the Question and Answer data format as input format. Can be used to de-duplicate an existing file.
  -h, --help     Show this message and exit

Arguments:
  <source>       Source filename with input data. Use - for stdin.
  <destination>  Destination filename to write formatted data to. Use - for stdout. File will be overwritten by default.
```

Example conversion from `stdin` to `stdout`:

```shell
$ java -jar ./wlc.jar - -
<learning language>	<base language>
^D
Found 1 pairs.
Q:
<base language>
A:
<learning language>
```

_Hint: `^D` is created by pressing `Ctrl`+ `d`. On Windows analogously use `^Z`._

## Known bugs

- When source equals destination, the file.

## License

This application is licensed under the EUPL.

**Library licenses**

- [Clikt](https://github.com/ajalt/clikt) licensed under Apache 2.0