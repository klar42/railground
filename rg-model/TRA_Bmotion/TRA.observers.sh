#!/bin/bash

echo '
{
    "observers": [
' > TRA.observers.json

sep=""

for t in T01 T02 T03 T04 T56 T00
do
echo "
        ${sep}{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"(${t}A_${t}B : PATH_SGMT_CURR) or (${t}B_${t}A : PATH_SGMT_CURR)\",
                \"selector\": \"#${t}\",
                \"true\": \"origin.attr('stroke-width', '3')\",
                \"false\": \"origin.attr('stroke-width', '1')\"
            }
        }
" >> TRA.observers.json
sep=","
done

for c in C00
do
echo "
        ${sep}{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"(${c}A_${c}D : PATH_SGMT_CURR) or (${c}D_${c}A : PATH_SGMT_CURR)\",
                \"selector\": \"#${c}AD\",
                \"true\": \"origin.attr('stroke-width', '3')\",
                \"false\": \"origin.attr('stroke-width', '1')\"
            }
        }
        ,{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"(${c}B_${c}C : PATH_SGMT_CURR) or (${c}C_${c}B : PATH_SGMT_CURR)\",
                \"selector\": \"#${c}BC\",
                \"true\": \"origin.attr('stroke-width', '3')\",
                \"false\": \"origin.attr('stroke-width', '1')\"
            }
        }
" >> TRA.observers.json
sep=","
done

for p in P01 P02 P03 P04 P05 P06 P07 P08 P17 P18
do
echo "
        ${sep}{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"RAIL_ELEM_POS_CURR(${p}) = RAIL_POS_L\",
                \"selector\": \"#${p}L\",
                \"true\": \"origin.attr('stroke-dasharray', '')\",
                \"false\": \"origin.attr('stroke-dasharray', '1 ')\"
            }
        }
        ,{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"RAIL_ELEM_POS_CURR(${p}) = RAIL_POS_R\",
                \"selector\": \"#${p}R\",
                \"true\": \"origin.attr('stroke-dasharray', '')\",
                \"false\": \"origin.attr('stroke-dasharray', '1')\"
            }
        }
        ,{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"(${p}T_${p}L : PATH_SGMT_CURR) or (${p}L_${p}T : PATH_SGMT_CURR) or (${p}T_${p}R : PATH_SGMT_CURR) or (${p}R_${p}T : PATH_SGMT_CURR)\",
                \"selector\": \"#${p}T\",
                \"true\": \"origin.attr('stroke-width', '3')\",
                \"false\": \"origin.attr('stroke-width', '1')\"
            }
        }
        ,{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"(${p}T_${p}L : PATH_SGMT_CURR) or (${p}L_${p}T : PATH_SGMT_CURR)\",
                \"selector\": \"#${p}L\",
                \"true\": \"origin.attr('stroke-width', '3')\",
                \"false\": \"origin.attr('stroke-width', '1')\"
            }
        }
        ,{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"(${p}T_${p}R : PATH_SGMT_CURR) or (${p}R_${p}T : PATH_SGMT_CURR)\",
                \"selector\": \"#${p}R\",
                \"true\": \"origin.attr('stroke-width', '3')\",
                \"false\": \"origin.attr('stroke-width', '1')\"
            }
        }
        ,{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"${p} : dom(union(PATH_ELEM_POS[PATH_REQ])) & union(PATH_ELEM_POS[PATH_REQ])(${p}) ≠ RAIL_ELEM_POS_CURR(${p})\",
                \"selector\": \"#${p}X\",
                \"true\": \"origin.attr('fill', 'blue')\",
                \"false\": \"origin.attr('fill', 'black')\"
            }
        }
" >> TRA.observers.json
sep=","
done

for s in S01 S02 S03 S04 S05 S06 S07 S08 S15 S16 S17 S18 S27 S28
do
echo "
        ${sep}{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"SIGNAL_ASPECT_CURR(${s}) = SIGNAL_ASPECT_STOP\",
                \"selector\": \"#${s}\",
                \"true\": \"origin.attr('fill', 'red')\",
                \"false\": \"\"
            }
        }
        ,{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"SIGNAL_ASPECT_CURR(${s}) = SIGNAL_ASPECT_CAUTION\",
                \"selector\": \"#${s}\",
                \"true\": \"origin.attr('fill', 'yellow')\",
                \"false\": \"\"
            }
        }
        ,{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"SIGNAL_ASPECT_CURR(${s}) = SIGNAL_ASPECT_PROCEED\",
                \"selector\": \"#${s}\",
                \"true\": \"origin.attr('fill', 'green')\",
                \"false\": \"\"
            }
        }
        ,{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"SIGNAL_CTOR(${s}) : PATH_CTOR_BEG[PATH_CURR]\",
                \"selector\": \"#${s}X\",
                \"true\": \"origin.attr('fill', 'blue')\",
                \"false\": \"origin.attr('fill', 'black')\"
            }
        }
" >> TRA.observers.json
sep=","
done

for r in R15 R17 R26 R28 R37 R35 R48 R46 R56 R58 R65 R67 R78 R76 R79 R87 R85 R89 R52 R61 R74 R83 R70 R80
do
echo "
        ${sep}{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"${r} : PATH_CURR\",
                \"selector\": \"#${r}\",
                \"true\": \"origin.attr('font-weight', 'bold')\",
                \"false\": \"origin.attr('font-weight', 'normal')\"
            }
        }
        ,{
            \"type\": \"predicate\",
            \"data\": {
                \"predicate\": \"${r} : PATH_REQ\",
                \"selector\": \"#${r}\",
                \"true\": \"origin.attr('font-style', 'italic')\",
                \"false\": \"origin.attr('font-style', 'normal')\"
            }
        }
" >> TRA.observers.json
sep=","
done

echo '
    ]
}
' >> TRA.observers.json

