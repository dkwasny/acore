# The main goal of this script was to generate the SpellDbc model class for me.
# There are too many fields to be doing that by hand.
#
# The source data is found on the wowdev wiki:
# https://wowdev.wiki/DB/Spell

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
            javaType = 'DbcLocaleString'
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
