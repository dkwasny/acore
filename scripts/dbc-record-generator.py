# A simple script for converting DBC schemas found on the wowdev wiki into
# record class bodies for the server project.
# Schemas can be found at: https://wowdev.wiki/Category:DBC_WotLK
#
# The output will need manual editing to look nice.
#
# Copy the type and name columns for the 3.3.5 table in whatever dbc
# type you care about.
# Put the text into a file and pass it into this script.
#
# Sample input:
# unit32<whitespace>field1
# uint32<whitespacee>field2[3]

import sys

numArgs = len(sys.argv)
if (numArgs) != 2:
    raise Exception(f'Invalid amount of arguments {numArgs}')

filename = sys.argv[1]

with open(filename) as file:
    lines = [x.strip() for x in file]

for line in lines:
    parts = line.split(maxsplit=1)
    if len(parts) != 2:
        raise Exception(f'Invalid number of parts: {parts}')
    inType = parts[0].strip()
    inName = parts[1].strip()

    match inType:
        case 'uint32':
            dbcType = 'UINT32'
            javaType = 'Long'
        case 'int32':
            dbcType = 'INT32'
            javaType = 'Integer'
        case 'float':
            dbcType = 'FLOAT'
            javaType = 'Float'
        case 'langstringrefⁱ':
            dbcType = 'STRING_REF'
            javaType = 'DbcStringRef'
        case _:
            dbcType = inType
            javaType = inType

    nameSplit = inName.split('[')

    fieldName = nameSplit[0][:1].lower() + nameSplit[0][1:]

    if len(nameSplit) > 1:
        count = int(nameSplit[1].split(']')[0])
    else:
        count = 1

    annotation = f'@DbcField(type = DbcType.{dbcType}'
    if count > 1:
        annotation += f', count = {count}'
    annotation += ')'


    field = ''
    if count > 1:
        field += 'List<'
    field += javaType
    if count > 1:
        field += '>'
    field += f' {fieldName},'

    print(f'{annotation}\n{field}\n')
