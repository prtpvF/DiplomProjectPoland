import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    watch: {
      usePolling: true,
    },
    host: true,
    port: 5174,
    proxy: {
      '/api/auth': {
          target: 'http://localhost:8089',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/api/person': {
          target: 'http://localhost:8081',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/api/address': {
          target: 'http://localhost:8081',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/api/worker': {
          target: 'http://localhost:9090',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/api/admin': {
          target: 'http://localhost:8080',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/api/public': {
          target: 'http://localhost:8082',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
  build: {
    outDir: './dist',
    emptyOutDir: true,
    sourcemap: false,
  },
  preview: {
    host: true,
    port: 3000,
    proxy: {
      '/api/auth': {
          target: 'http://authservice:8089',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/api/person': {
          target: 'http://personservice:8081',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/api/address': {
          target: 'http://personservice:8081',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/api/worker': {
          target: 'http://producerservice:9090',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/api/admin': {
          target: 'http://adminservice:8080',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
      '/api/public': {
          target: 'http://publicapiservice:8082',
          changeOrigin: true,
          secure: false,      
          rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  }
})
