#include <iostream>
#include <unordered_map>
#include <vector>
#include <thread>
#include <mutex>
#include <chrono>
#include <cstring>

#include <boost/asio.hpp>
#include <boost/bind/bind.hpp>





#include <unistd.h>


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






