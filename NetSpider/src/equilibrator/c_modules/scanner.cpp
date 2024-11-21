#include <iostream>
#include <unordered_map>
#include <vector>
#include <thread>
#include <mutex>
#include <chrono>
#include <cstring>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <unistd.h>
#include <netdb.h>

#include "scanner.h"


int main() {
    // Crear el scanner con una IP
    PortScanner scanner("192.168.1.145"); // O la IP que desees escanear
    
    // Ejecutar el escaneo
    scanner.scan();
    
    // Opcional: obtener y mostrar los resultados
    auto openPorts = scanner.getOpenPorts();
    for (const auto& port : openPorts) {
        std::cout << "Puerto " << port.first << " abierto - Servicio: " 
                 << port.second << std::endl;
    }
    
    std::cout << "Puertos cerrados: " << scanner.getClosedPorts() << std::endl;
    std::cout << "Sistema operativo detectado: " << scanner.getOperatingSystemX() << std::endl;

    return 0;
}






