import { useEffect } from 'react';
import { Button, View, Text } from 'react-native';
import { NativeModules } from 'react-native';

export default function App() {
  useEffect(() => {
    // só funciona em dispositivos Android físicos (não em simuladores)
    NativeModules.StartServiceModule?.start();
  }, []);

  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Monitorando mudanças de horário...</Text>
    </View>
  );
}
