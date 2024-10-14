#!/bin/bash

# Archivo de salida
output_file="matriz_100x100.txt"

# Limpiar el archivo de salida si ya existe
> $output_file

# Generar la matriz 100x100
for i in {1..100}
do
    # Inicializar cada fila con 100 ceros
    row=()
    for j in {1..100}; do
        row+=("0")
    done
    
    # Llenar los valores no nulos según el patrón
    if [ $i -eq 1 ]; then
        # Primera fila: 2x1 + x2 = 4.5
        row[0]="2"   # x1
        row[1]="1"   # x2
    elif [ $i -eq 100 ]; then
        # Última fila: x99 + 2x100 = 4.5
        row[98]="1"  # x99
        row[99]="2"  # x100
    else
        # Filas intermedias: xi-1 + 2xi + xi+1 = 6
        row[$((i-2))]="1"  # xi-1
        row[$((i-1))]="2"  # xi
        row[$i]="1"        # xi+1
    fi
    
    # Escribir la fila en el archivo
    echo "${row[*]}" >> $output_file
done

echo "La matriz 100x100 se ha volcado en el archivo $output_file."
