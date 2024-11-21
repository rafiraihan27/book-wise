"use client"

import * as React from "react"
import {
  BookOpen,
  Bot,
  Command,
  Frame,
  HelpCircle,
  LifeBuoy,
  Map,
  PieChart,
  Send,
  Settings2,
  SquareTerminal,
} from "lucide-react"

import { NavMain } from "@/components/user-page/nav-main"
import { NavProjects } from "@/components/user-page/nav-projects"
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar"
import { Button } from "../ui/button"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"

import { appInfo, assets } from "@/app/config"

const data = {
  user: {
    name: "shadcn",
    email: "m@example.com",
    avatar: "/avatars/shadcn.jpg",
  },
  navMain: [
    {
      title: "Playground",
      url: "#",
      icon: SquareTerminal,
      isActive: true,
      items: [
        {
          title: "History",
          url: "#",
        },
        {
          title: "Starred",
          url: "#",
        },
        {
          title: "Settings",
          url: "#",
        },
      ],
    },
    {
      title: "Models",
      url: "#",
      icon: Bot,
      items: [
        {
          title: "Genesis",
          url: "#",
        },
        {
          title: "Explorer",
          url: "#",
        },
        {
          title: "Quantum",
          url: "#",
        },
      ],
    },
    {
      title: "Documentation",
      url: "#",
      icon: BookOpen,
      items: [
        {
          title: "Introduction",
          url: "#",
        },
        {
          title: "Get Started",
          url: "#",
        },
        {
          title: "Tutorials",
          url: "#",
        },
        {
          title: "Changelog",
          url: "#",
        },
      ],
    },
    {
      title: "Settings",
      url: "#",
      icon: Settings2,
      items: [
        {
          title: "General",
          url: "#",
        },
        {
          title: "Team",
          url: "#",
        },
        {
          title: "Billing",
          url: "#",
        },
        {
          title: "Limits",
          url: "#",
        },
      ],
    },
  ],
  projects: [
    {
      name: "Design Engineering",
      url: "#",
      icon: Frame,
    },
    {
      name: "Sales & Marketing",
      url: "#",
      icon: PieChart,
    },
    {
      name: "Travel",
      url: "#",
      icon: Map,
    },
  ],
}

export default function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  return (
    <Sidebar variant="sidebar" {...props}>
      <SidebarHeader className='bg-white'>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton size="lg" asChild>
              <a href="#">
                <div className="flex aspect-square size-8 items-center justify-center rounded-lg">
                  <img
                    src={assets.logoUrl}
                    className="w-8"
                    alt="logo"
                  />
                </div>
                <div className="grid flex-1 text-left text-sm leading-tight">
                  <span className="truncate font-semibold">{appInfo.appName}</span>
                  <span className="truncate text-xs">{appInfo.tagLine}</span>
                </div>
              </a>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>
      <SidebarContent className="bg-white">
        <Tabs defaultValue="book" className="w-full mt-3">
          <TabsList className="mx-auto flex items-center w-fit rounded-full gap-2 p-2 h-10">
            <TabsTrigger value="book" className="rounded-full">Book</TabsTrigger>
            <TabsTrigger value="non-book" className="rounded-full">Non Book</TabsTrigger>
          </TabsList>
          <TabsContent value="book">
            <NavMain items={data.navMain} />
            <NavProjects projects={data.projects} />
          </TabsContent>
          <TabsContent value="non-book">
            <NavMain items={data.navMain} />
          </TabsContent>
        </Tabs>
      </SidebarContent>

      <SidebarFooter className="border-t p-4 bg-white">
        <div className="rounded-lg bg-gray-100 p-4">
          <div className="flex items-center gap-4">
            <div className="rounded-full bg-white p-2">
              <HelpCircle className="h-6 w-6 text-teal-500" />
            </div>
            <div>
              <h4 className="font-semibold">Need help?</h4>
              <p className="text-sm text-gray-500">Contact our helpdesk</p>
            </div>
          </div>
          <Button variant="outline" className="mt-4 w-full font-semibold">
            CONTACT
          </Button>
        </div>
      </SidebarFooter>
    </Sidebar>
  )
}
