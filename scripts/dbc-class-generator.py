# This script converts the human readable DBC schemas found on TrinityCore's
# wiki into simple Java class fields.
# The fields can then be pasted into a Java class.
# Helper methods (e.g. toString()) can be generated in your IDE.
#
# The resulting classes are just a dirt simple collection of public fields.
# I wanted to use records (or immutable beans), but some DBC types have far
# too many fields for a single Java constructor.
# Maybe look into generating a builder pattern if the current design causes
# problems (unlikely).
#
# Sample input data:
# field1<whitespace>uint32
# field2<whitespace>int32
# field3<whitespace>string
#
# Source Data: https://trinitycore.info/files/DBC/335/DBC

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
    inName = parts[0].strip()
    inType = parts[1].strip()

    # Javaify the field names
    fieldName = inName[:1].lower() + inName[1:]
    fieldName = fieldName.replace('ID', 'Id')
    fieldName = fieldName.replace('_', '')
    fieldName = fieldName.replace('iD', 'id')

    match inType:
        case 'uint32':
            dbcType = 'UINT32'
            javaType = 'long'
        case 'int32':
            dbcType = 'INT32'
            javaType = 'int'
        case 'float':
            dbcType = 'FLOAT'
            javaType = 'float'
        case 'string':
            dbcType = 'STRING'
            javaType = 'String'
        case _:
            dbcType = inType
            javaType = inType

    annotation = f'@DbcField(DbcType.{dbcType})'
    field = f'public {javaType} {fieldName};'

    print(f'{annotation}\n{field}\n')
