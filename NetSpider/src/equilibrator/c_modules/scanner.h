#ifndef SCANNER_H
#define SCANNER_H

#include <unordered_map>
#include <string>

// declaro
class MainScanner {
public:
    MainScanner(); 
    // scan
    void scanPort(int port);



private:
    // Atributos
    std::string ip;
    const std::unordered_map<int, std::string>& portServiceMap;

    std::unordered_map<int, std::string> open_ports;
    std::string operating_system;
    std::mutex port_mutex;
};

#endif