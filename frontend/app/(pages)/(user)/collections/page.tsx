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
import { Button } from '@/components/ui/button';
import Link from 'next/link';
import BookCollection from '@/components/user-page/book-collections';

export default function Page({ searchParams }: { searchParams: { [key: string]: string } }) {
  const search = searchParams.search || '';
  const category = searchParams.category || '';
  const year = searchParams.year || '';

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
                      href={`/collections`}
                    >
                      Collections
                    </BreadcrumbLink>
                  </BreadcrumbItem>
                  <BreadcrumbSeparator className="hidden md:block" />
                  <BreadcrumbItem className="hidden md:block">
                    <BreadcrumbLink
                      href={`/collections${category
                        ? `?category=${encodeURIComponent(category)}`
                        : search
                          ? `?search=${encodeURIComponent(search)}`
                          : ''
                        }`}
                    >
                      {toTitleCase(category || search)}
                    </BreadcrumbLink>
                  </BreadcrumbItem>
                  {search || category ? <BreadcrumbSeparator className="hidden md:block" /> : ""}
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

        <div className="grid grid-cols-12 gap-5 container mx-auto p-4">
          <div className="col-span-12 md:col-span-12 flex flex-col gap-7">
            {/* <div className="flex justify-between lg:gap-5">
              <div className="col-span-11">
                <div className="bg-white shadow-md rounded-xl p-6 flex flex-col md:flex-row items-start md:items-center gap-6 mx-auto">
                  
                  <div className="flex-1">
                    <p className="text-sm text-gray-500 font-semibold">Continue Reading</p>
                    <h2 className="text-xl font-bold text-gray-900 mt-2">
                      The Psychology of Money
                    </h2>
                    <p className="text-sm text-gray-600 mt-2">
                      The Psychology of Money by Morgan Housel offers timeless insights into
                      how emotions, behavior, and psychology influence our financial decisions.
                      Through engaging stories and practical lessons, the book explores why
                      managing money isn't just about knowledge but ....
                    </p>
                    <button className="mt-4 bg-green-500 text-white font-semibold py-2 px-6 rounded-lg shadow-md hover:bg-green-600 transition duration-200">
                      Read Now
                    </button>
                  </div>

                  <div className="w-32 h-40 bg-gray-200 rounded-lg self-center md:self-auto"></div>
                </div>

              </div>
              <div className="col-span-1 items-center">
                <div className="bg-[#19B878] h-auto text-white rounded-xl p-6 shadow-lg max-w-sm hidden lg:block">
                  <div className="text-center">
                    <h2 className="text-lg font-bold mb-4">Join our book lovers community here now</h2>
                    <Link href="/community">
                      <Button className="bg-white text-[#19B878] font-semibold py-2 px-4 rounded-full shadow-md hover:bg-gray-100 transition duration-200">
                        Join Now
                      </Button>
                    </Link>
                  </div>
                </div>
              </div>
            </div> */}
            <BookCollection search={search} category={category} years={year}/>
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
