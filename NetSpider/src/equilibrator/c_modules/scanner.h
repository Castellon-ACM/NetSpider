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
    // ref
    const std::unordered_map<int, std::string>& portServiceMap;
};

#endif