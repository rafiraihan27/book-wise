import React from 'react';

import AppSidebar from '@/components/user-page/app-sidebar';
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from '@/components/ui/breadcrumb';
import { Separator } from '@/components/ui/separator';
import {
  SidebarInset,
  SidebarProvider,
  SidebarTrigger,
} from '@/components/ui/sidebar';

import { recomendations } from '@/app/config';

export default function Page({ searchParams }: { searchParams: { [key: string]: string } }) {
  const search = searchParams.search || '';
  const category = searchParams.category || '';

  return (
    <SidebarProvider>
      <AppSidebar className="pt-3" />
      <SidebarInset className="bg-muted/50">
        <div className="bg-white">
          <header className="container mx-auto flex h-10 shrink-0 items-center justify-between gap-2 px-4">
            {/* Left Section */}
            <div className="flex items-center gap-2">
              <SidebarTrigger className="-ml-1" />
              <Separator orientation="vertical" className="mr-2 h-4" />
              <Breadcrumb>
                <BreadcrumbList>
                  <BreadcrumbItem className="hidden md:block">
                    <BreadcrumbLink
                      href={`/collections${category
                          ? `?category=${encodeURIComponent(category)}`
                          : search
                            ? `?search=${encodeURIComponent(search)}`
                            : ''
                        }`}
                    >
                      {toTitleCase(category || search || 'All books')}
                    </BreadcrumbLink>
                  </BreadcrumbItem>
                  <BreadcrumbSeparator className="hidden md:block" />
                  <BreadcrumbItem>
                    <BreadcrumbPage>
                      {category ? toTitleCase(search || '') : ''}
                    </BreadcrumbPage>
                  </BreadcrumbItem>
                </BreadcrumbList>
              </Breadcrumb>
            </div>

            {/* Right Section */}
            <Breadcrumb>
              <BreadcrumbList className="">
                {recomendations.map((breadcrumb, index) => (
                  <React.Fragment key={index}>
                    <BreadcrumbLink href={breadcrumb.href}>
                      {breadcrumb.name}
                    </BreadcrumbLink>
                    {index < recomendations.length - 1 && (
                      <Separator orientation="vertical" className="mr-2 h-4" />
                    )}
                  </React.Fragment>
                ))}
              </BreadcrumbList>
            </Breadcrumb>
          </header>
        </div>

        <div className="grid grid-cols-12 gap-4 container mx-auto">
          <div className="col-span-9 flex flex-col gap-4 p-4">
            <div className="grid auto-rows-min gap-4 md:grid-cols-3">
              <div className="aspect-video rounded-xl bg-muted" />
              <div className="aspect-video rounded-xl bg-muted" />
              <div className="aspect-video rounded-xl bg-muted" />
            </div>
            <div className="min-h-[100vh] flex-1 rounded-xl bg-muted md:min-h-min" />
          </div>
          <div className="col-span-3 flex flex-col gap-4 p-4">
            <div className="flex-1 rounded-xl bg-muted md:min-h-min" />
            <div className="flex-1 rounded-xl bg-muted md:min-h-min" />
          </div>
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
}

function toTitleCase(text: string | undefined): string {
  if (!text) return '';
  return text
    .split(' ')
    .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
    .join(' ');
}
