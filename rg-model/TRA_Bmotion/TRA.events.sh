#!/bin/bash

echo '
{
    "events": [
' > TRA.events.json

sep=""

for p in P01 P02 P03 P04 P05 P06 P07 P08 P17 P18
do
echo "
        ${sep}{
            \"type\": \"executeEvent\",
            \"data\": {
                \"events\": [
                    {
                        \"name\": \"set_RAIL_ELEM_PATH\",
                        \"predicate\": \"elem=${p}\"
                    }
                ],
                \"selector\": \"#${p}X\"
            }
        }
" >> TRA.events.json
sep=","
done

for s in S01 S02 S03 S04 S05 S06 S07 S08 S15 S16 S17 S18 S27 S28
do
echo "
        ${sep}{
            \"type\": \"executeEvent\",
            \"data\": {
                \"events\": [
                    {
                        \"name\": \"set_SIGNAL_ASPECT_DEFAULT\",
                        \"predicate\": \"sig=${s}\"
                    },
                    {
                        \"name\": \"set_SIGNAL_ASPECT_PATH\",
                        \"predicate\": \"sig=${s}\"
                    }
                ],
                \"selector\": \"#${s}X\"
            }
        }
" >> TRA.events.json
sep=","
done

for r in R15 R17 R26 R28 R37 R35 R48 R46 R56 R58 R65 R67 R78 R76 R79 R87 R85 R89 R52 R61 R74 R83 R70 R80
do
echo "
        ${sep}{
            \"type\": \"executeEvent\",
            \"data\": {
                \"events\": [
                    {
                        \"name\": \"add_PATH_REQ\",
                        \"predicate\": \"path=${r}\"
                    }
                    ,{
                        \"name\": \"rem_PATH_REQ\",
                        \"predicate\": \"path=${r}\"
                    }
                    ,{
                        \"name\": \"add_PATH_CURR\",
                        \"predicate\": \"path=${r}\"
                    }
                    ,{
                        \"name\": \"rem_PATH_CURR\",
                        \"predicate\": \"path=${r}\"
                    }
                    ,{
                        \"name\": \"set_PATH_REL\",
                        \"predicate\": \"path=${r}\"
                    }
                ],
                \"selector\": \"#${r}\"
            }
        }
" >> TRA.events.json
sep=","
done

echo '
    ]
}
' >> TRA.events.json

