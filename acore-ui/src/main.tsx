import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { BrowserRouter, Routes, Route, Navigate } from "react-router"
import React, { Suspense } from 'react'

const LazySwaggerComponent = React.lazy(() => import("./Swagger.tsx"));
const lazySwagger = (
  <Suspense fallback={<div>Loading Swagger...</div>}>
    <LazySwaggerComponent />
  </Suspense>
)

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="" element={<Navigate to="app" replace={true} />} />
        <Route path="app" element={<App />} />
        <Route path="swagger" element={lazySwagger} />
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
