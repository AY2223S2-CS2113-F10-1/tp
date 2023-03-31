# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 

### Editing a Tag: `tag edit`
Edit the name of a existing tag.

Format: `tag edit -n arg<tagNameNew> -o arg<tagNameOld>`

* The `tagNameOld` should be the name of an existing tag.

Example of usage: 

`tag edit -n CS2113 -o CS2113T`

Sample output:
```
tag edit -n CS2113 -o CS2113T
Tag 01a6967c-f25f-4f61-ace1-324a07bc3761tag name has been changed from CS2113 to CS2113T
```

### Deleting a Tag: `tag delete`
Delete an existing tag. All cards under the affected tag will not be deleted, but have the tag removed from them.

Format: `tag delete -t arg<tagName>`

* The `tagName` should be the name of an existing tag.

Example of usage: 

`tag delete -t CS2040C`

Sample output: 
```
tag delete -t CS2040C
Successfully removed tag d396e012-b6ca-45e4-aa70-ac5fd994a8a8 from card 3b86b31c-6289-4716-a5c6-5afd43b9bbd3
Successfully removed tag d396e012-b6ca-45e4-aa70-ac5fd994a8a8 from the tag list.
```

### List all Tags: `tag list`
List all tags 

Format: `tag list`

Example of usage: 

`tag list`

Sample output:
```
Here is your current list of tags:
1.Tag name : CS2040C, tag uuid : d396e012-b6ca-45e4-aa70-ac5fd994a8a8
2.Tag name : CS2113, tag uuid : 01a6967c-f25f-4f61-ace1-324a07bc3761
```


### List Cards under Tag: `tag list`
List all cards which are under the specified tag

Format: `tag list -t arg<tagName>`

* The `tagName` should be the name of an existing tag.

Example of usage: 

`
tag list -t CS2040C
`

Sample output:
```
tag list -t CS2040C
Here is a list of your cards :
1.Qn: fdf
Ans: ffffffffff
UUID:  3b86b31c-6289-4716-a5c6-5afd43b9bbd3
```

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: You do so by following these steps:

1. Export the deck as a Json file using the command "export"
2. Navigate to the directory of your Inka installation.
3. The file will be named savedata.json. Copy this file.
4. Paste this file in the Inka's directory in the new computer

Your file will be loaded the next time you run Inka!

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
