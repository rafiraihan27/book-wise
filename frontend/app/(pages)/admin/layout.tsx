"use client"

import NavbarAdmin from '@/components/navbar-admin';

interface UserLayoutProps {
  children: React.ReactNode
}

export default function AdminLayout({ children }: UserLayoutProps) {
  return (
    <>
        <NavbarAdmin/>
        {children}
    </>
  )
}

