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


int main(int argc, char *argv[]) {

    std::string ip = argv[1];


  
    PortScanner scanner(ip);
    
    // Ejecutar el escaneo
    scanner.scan();
    
  
    auto openPorts = scanner.getOpenPorts();
    for (const auto& port : openPorts) {
        std::cout << "puerto|" << port.first << "|" 
                 << port.second << std::endl;
    }
    
    std::cout << "cerrados|" << scanner.getClosedPorts() << std::endl;
    std::cout << "sistemaoperativo|" << scanner.getOperatingSystemX() << std::endl;

    return 0;
}






